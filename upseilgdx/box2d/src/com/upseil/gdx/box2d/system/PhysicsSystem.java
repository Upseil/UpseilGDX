package com.upseil.gdx.box2d.system;

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
import com.upseil.gdx.artemis.component.ActorComponent;
import com.upseil.gdx.artemis.util.ArtemisCollections;
import com.upseil.gdx.box2d.ContactReaction;
import com.upseil.gdx.box2d.component.BodyComponent;
import com.upseil.gdx.box2d.component.Box2DWorld;
import com.upseil.gdx.box2d.component.OnBeginContact;
import com.upseil.gdx.pool.PooledPools;
import com.upseil.gdx.utils.GDXCollections;

public class PhysicsSystem extends BaseSystem implements ContactListener {

    private ComponentMapper<Box2DWorld> worldMapper;
    private ComponentMapper<BodyComponent> bodyMapper;
    private ComponentMapper<ActorComponent> actorMapper;
    private ComponentMapper<OnBeginContact> beginContactMapper;
    
    private EntitySubscription worlds;
    private EntitySubscription bodies;
    private EntitySubscription bodiedActors;
    
    private final ObjectIntMap<Body> bodyToEntity;
    private final Array<ContactReaction.Context> beginningContacts;

    private final float maxFrameTime;
    private final float timeStep;
    private final int velocityIterations;
    private final int positionIterations;
    private float accumulator;
    
    public PhysicsSystem(float timeStep, int velocityIterations, int positionIterations) {
        this(Float.MAX_VALUE, timeStep, velocityIterations, positionIterations);
    }
    
    public PhysicsSystem(float maxFrameTime, float timeStep, int velocityIterations, int positionIterations) {
        this.maxFrameTime = maxFrameTime;
        this.timeStep = timeStep;
        this.velocityIterations = velocityIterations;
        this.positionIterations = positionIterations;
        
        bodyToEntity = new ObjectIntMap<>();
        beginningContacts = new Array<>();
    }
    
    @Override
    protected void initialize() {
        AspectSubscriptionManager subscriptionManager = world.getAspectSubscriptionManager();
        
        worlds = subscriptionManager.get(Aspect.all(Box2DWorld.class));
        SubscriptionListener worldsListener = new SubscriptionListener() {
            @Override
            public void removed(IntBag entities) { }
            @Override
            public void inserted(IntBag entities) {
                ArtemisCollections.forEach(entities, id -> worldMapper.get(id).get().setContactListener(PhysicsSystem.this));
            }
        };
        worldsListener.inserted(worlds.getEntities());
        worlds.addSubscriptionListener(worldsListener);
        
        bodies = subscriptionManager.get(Aspect.all(BodyComponent.class));
        SubscriptionListener bodiesListener = new SubscriptionListener() {
            @Override
            public void removed(IntBag entities) {
                ArtemisCollections.forEach(entities, id -> bodyToEntity.remove(bodyMapper.get(id).get(), 0));
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
        while (accumulator >= timeStep) {
            for (int index = 0; index < worldsBag.size(); index++) {
                Box2DWorld world = worldMapper.get(worldIds[index]);
                world.step(timeStep, velocityIterations, positionIterations);
            }
            accumulator -= timeStep;
        }
        
        ArtemisCollections.forEach(bodiedActors.getEntities(), id -> {
            BodyComponent body = bodyMapper.get(id);
            ActorComponent actor = actorMapper.get(id);
            
            Vector2 bodyPosition = body.getPosition();
            actor.setPosition(bodyPosition.x - (actor.getWidth() / 2), bodyPosition.y - (actor.getHeight() / 2));
            actor.setRotation(body.getRotation());
        });
        
        GDXCollections.forEach(beginningContacts, context -> {
            beginContactMapper.get(context.getSelfId()).accept(context);
            context.free();
        });
        beginningContacts.clear();
    }
    
    public int getEntityForBody(Body body) {
        return bodyToEntity.get(body, -1);
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        int entityA = getEntityForBody(fixtureA.getBody());
        Fixture fixtureB = contact.getFixtureB();
        int entityB = getEntityForBody(fixtureB.getBody());
        
        if (entityA == entityB) return;
        
        if (entityA != -1 && beginContactMapper.has(entityA)) {
            beginningContacts.add(obtainContactContext(entityA, fixtureA, entityB, fixtureB, contact));
        }
        if (entityB != -1 && beginContactMapper.has(entityB)) {
            beginningContacts.add(obtainContactContext(entityB, fixtureB, entityA, fixtureA, contact));
        }
    }

    private ContactReaction.Context obtainContactContext(int selfId, Fixture selfFixture, int otherId, Fixture otherFixture, Contact contact) {
        return PooledPools.obtain(ContactReaction.Context.class).intialize(selfId, selfFixture, otherId, otherFixture, contact);
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
    
}
