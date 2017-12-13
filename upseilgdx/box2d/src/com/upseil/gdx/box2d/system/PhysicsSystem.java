package com.upseil.gdx.box2d.system;

import java.util.Iterator;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.EntitySubscription.SubscriptionListener;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.upseil.gdx.action.Action;
import com.upseil.gdx.action.FireBeginContactEvent;
import com.upseil.gdx.action.FireContactEvent;
import com.upseil.gdx.action.FireEndContactEvent;
import com.upseil.gdx.action.FirePostSolveEvent;
import com.upseil.gdx.action.FirePreSolveEvent;
import com.upseil.gdx.artemis.component.ActorComponent;
import com.upseil.gdx.artemis.util.ArtemisCollections;
import com.upseil.gdx.box2d.component.BodyComponent;
import com.upseil.gdx.box2d.component.Box2DWorld;
import com.upseil.gdx.box2d.component.OnBeginContact;
import com.upseil.gdx.box2d.component.OnEndContact;
import com.upseil.gdx.box2d.component.PostSolveContact;
import com.upseil.gdx.box2d.component.PreSolveContact;
import com.upseil.gdx.box2d.event.PostSolveEvent;
import com.upseil.gdx.box2d.event.PreSolveEvent;
import com.upseil.gdx.box2d.event.SimpleContactEvent;
import com.upseil.gdx.pool.PooledPools;

public class PhysicsSystem extends BaseSystem implements ContactListener {

    private ComponentMapper<Box2DWorld> worldMapper;
    private ComponentMapper<BodyComponent> bodyMapper;
    private ComponentMapper<ActorComponent> actorMapper;

    private ComponentMapper<PreSolveContact> preSolveContactMapper;
    private ComponentMapper<OnBeginContact> beginContactMapper;
    private ComponentMapper<OnEndContact> endContactMapper;
    private ComponentMapper<PostSolveContact> postSolveContactMapper;
    
    private EntitySubscription worlds;
    private EntitySubscription bodies;
    private EntitySubscription bodiedActors;
    
    private final ObjectIntMap<Body> bodyToEntity;
    private final Array<FireContactEvent<?, ?>> scheduledContactEvents;

    private final float maxFrameTime;
    private final float stepTime;
    private final int velocityIterations;
    private final int positionIterations;
    private float accumulator;
    
    public PhysicsSystem(float stepTime, int velocityIterations, int positionIterations) {
        this(Float.MAX_VALUE, stepTime, velocityIterations, positionIterations);
    }
    
    public PhysicsSystem(float maxFrameTime, float stepTime, int velocityIterations, int positionIterations) {
        this.maxFrameTime = maxFrameTime;
        this.stepTime = stepTime;
        this.velocityIterations = velocityIterations;
        this.positionIterations = positionIterations;
        
        bodyToEntity = new ObjectIntMap<>();
        scheduledContactEvents = new Array<>();
    }
    
    @Override
    protected void initialize() {
        AspectSubscriptionManager subscriptionManager = world.getAspectSubscriptionManager();
        
        worlds = subscriptionManager.get(Aspect.all(Box2DWorld.class));
        SubscriptionListener worldsListener = new SubscriptionListener() {
            @Override
            public void removed(IntBag entities) {
                ArtemisCollections.forEachComponent(entities, worldMapper, world -> world.get().setContactListener(null));
            }
            @Override
            public void inserted(IntBag entities) {
                ArtemisCollections.forEachComponent(entities, worldMapper, world -> world.get().setContactListener(PhysicsSystem.this));
            }
        };
        worldsListener.inserted(worlds.getEntities());
        worlds.addSubscriptionListener(worldsListener);
        
        bodies = subscriptionManager.get(Aspect.all(BodyComponent.class));
        SubscriptionListener bodiesListener = new SubscriptionListener() {
            @Override
            public void removed(IntBag entities) {
                ArtemisCollections.forEachComponent(entities, bodyMapper, body -> bodyToEntity.remove(body.get(), 0));
            }
            @Override
            public void inserted(IntBag entities) {
                ArtemisCollections.forEach(entities, id -> bodyToEntity.put(bodyMapper.get(id).get(), id));
            }
        };
        bodiesListener.inserted(bodies.getEntities());
        bodies.addSubscriptionListener(bodiesListener);
        
        bodiedActors = subscriptionManager.get(Aspect.all(ActorComponent.class, BodyComponent.class));
    }

    @Override
    protected void processSystem() {
        IntBag worldsBag = worlds.getEntities();
        int[] worldIds = worldsBag.getData();
        
        accumulator += Math.min(world.delta, maxFrameTime);
        while (accumulator >= stepTime) {
            ArtemisCollections.forEachComponent(bodies.getEntities(), bodyMapper, body -> body.act(stepTime));
            
            for (int index = 0; index < worldsBag.size(); index++) {
                Box2DWorld world = worldMapper.get(worldIds[index]);
                world.step(stepTime, velocityIterations, positionIterations);
            }

            Iterator<FireContactEvent<?, ?>> eventsIterator = scheduledContactEvents.iterator();
            while (eventsIterator.hasNext()) {
                Action<?, ?> scheduledEvent = eventsIterator.next();
                if (scheduledEvent.act(stepTime)) {
                    scheduledEvent.free();
                    eventsIterator.remove();
                }
            }
            
            accumulator -= stepTime;
        }
        
        ArtemisCollections.forEachComponent(bodiedActors.getEntities(), bodyMapper, actorMapper,
            (body, actor) -> {
                Vector2 bodyPosition = body.getPosition();
                actor.setPosition(bodyPosition.x - (actor.getWidth() / 2), bodyPosition.y - (actor.getHeight() / 2));
                actor.setRotation(body.getRotation());
            }
        );
    }
    
    public int getEntityForBody(Body body) {
        return bodyToEntity.get(body, -1);
    }
    
    public float getPhysicsDeltaTime(float deltaTime) {
        float timeToProcess = Math.min(accumulator + deltaTime, maxFrameTime);
        return (int) (timeToProcess / stepTime) * stepTime;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Fixture fixtureA = contact.getFixtureA();
        int entityA = getEntityForBody(fixtureA.getBody());
        Fixture fixtureB = contact.getFixtureB();
        int entityB = getEntityForBody(fixtureB.getBody());
        
        if (entityA != -1 && preSolveContactMapper.has(entityA)) {
            schedulePreSolveEvent(entityA, fixtureA, entityB, fixtureB, contact, oldManifold);
        }
        if (entityB != -1 && preSolveContactMapper.has(entityB)) {
            schedulePreSolveEvent(entityB, fixtureB, entityA, fixtureA, contact, oldManifold);
        }
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        int entityA = getEntityForBody(fixtureA.getBody());
        Fixture fixtureB = contact.getFixtureB();
        int entityB = getEntityForBody(fixtureB.getBody());
        
        if (entityA != -1 && beginContactMapper.has(entityA)) {
            scheduleBeginContactEvent(entityA, fixtureA, entityB, fixtureB, contact);
        }
        if (entityB != -1 && beginContactMapper.has(entityB)) {
            scheduleBeginContactEvent(entityB, fixtureB, entityA, fixtureA, contact);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        int entityA = getEntityForBody(fixtureA.getBody());
        Fixture fixtureB = contact.getFixtureB();
        int entityB = getEntityForBody(fixtureB.getBody());
        
        if (entityA != -1 && endContactMapper.has(entityA)) {
            scheduleEndContactEvent(entityA, fixtureA, entityB, fixtureB, contact);
        }
        if (entityB != -1 && endContactMapper.has(entityB)) {
            scheduleEndContactEvent(entityB, fixtureB, entityA, fixtureA, contact);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        Fixture fixtureA = contact.getFixtureA();
        int entityA = getEntityForBody(fixtureA.getBody());
        Fixture fixtureB = contact.getFixtureB();
        int entityB = getEntityForBody(fixtureB.getBody());
        
        if (entityA != -1 && postSolveContactMapper.has(entityA)) {
            schedulePostSolveEvent(entityA, fixtureA, entityB, fixtureB, contact, impulse);
        }
        if (entityB != -1 && postSolveContactMapper.has(entityB)) {
            schedulePostSolveEvent(entityB, fixtureB, entityA, fixtureA, contact, impulse);
        }
    }

    private void schedulePreSolveEvent(int selfId, Fixture selfFixture, int otherId, Fixture otherFixture, Contact contact, Manifold oldManifold) {
        FirePreSolveEvent action = PooledPools.obtain(FirePreSolveEvent.class);
        PreSolveEvent event = PooledPools.obtain(PreSolveEvent.class).set(selfId, selfFixture, otherId, otherFixture, contact, oldManifold);
        action.setMapper(preSolveContactMapper);
        action.setState(event);
        scheduledContactEvents.add(action);
    }

    private void scheduleBeginContactEvent(int selfId, Fixture selfFixture, int otherId, Fixture otherFixture, Contact contact) {
        FireBeginContactEvent action = PooledPools.obtain(FireBeginContactEvent.class);
        SimpleContactEvent event = PooledPools.obtain(SimpleContactEvent.class).set(selfId, selfFixture, otherId, otherFixture, contact);
        action.setMapper(beginContactMapper);
        action.setState(event);
        scheduledContactEvents.add(action);
    }

    private void scheduleEndContactEvent(int selfId, Fixture selfFixture, int otherId, Fixture otherFixture, Contact contact) {
        FireEndContactEvent action = PooledPools.obtain(FireEndContactEvent.class);
        SimpleContactEvent event = PooledPools.obtain(SimpleContactEvent.class).set(selfId, selfFixture, otherId, otherFixture, contact);
        action.setMapper(endContactMapper);
        action.setState(event);
        scheduledContactEvents.add(action);
    }

    private void schedulePostSolveEvent(int selfId, Fixture selfFixture, int otherId, Fixture otherFixture, Contact contact, ContactImpulse impulse) {
        FirePostSolveEvent action = PooledPools.obtain(FirePostSolveEvent.class);
        PostSolveEvent event = PooledPools.obtain(PostSolveEvent.class).set(selfId, selfFixture, otherId, otherFixture, contact, impulse);
        action.setMapper(postSolveContactMapper);
        action.setState(event);
        scheduledContactEvents.add(action);
    }
    
}
