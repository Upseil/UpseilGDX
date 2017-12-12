package com.upseil.gdx.box2d.system;

import java.util.Iterator;
import java.util.function.Function;

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
import com.upseil.gdx.action.AbstractAction;
import com.upseil.gdx.action.Action;
import com.upseil.gdx.artemis.component.ActorComponent;
import com.upseil.gdx.artemis.util.ArtemisCollections;
import com.upseil.gdx.box2d.component.BodyComponent;
import com.upseil.gdx.box2d.component.Box2DWorld;
import com.upseil.gdx.box2d.component.OnBeginContact;
import com.upseil.gdx.box2d.component.OnEndContact;
import com.upseil.gdx.box2d.component.PostSolveContact;
import com.upseil.gdx.box2d.component.PreSolveContact;
import com.upseil.gdx.box2d.event.ContactEvent;
import com.upseil.gdx.box2d.event.ContactEventHandler;
import com.upseil.gdx.box2d.event.PostSolveEvent;
import com.upseil.gdx.box2d.event.PreSolveEvent;
import com.upseil.gdx.box2d.event.SimpleContactEvent;
import com.upseil.gdx.pool.PooledPool;
import com.upseil.gdx.pool.PooledPools;

public class PhysicsSystem extends BaseSystem implements ContactListener {

    private ComponentMapper<Box2DWorld> worldMapper;
    private ComponentMapper<BodyComponent> bodyMapper;
    private ComponentMapper<ActorComponent> actorMapper;

    private ComponentMapper<PreSolveContact> preSolveContactMapper;
    private Function<PreSolveEvent, ContactEventHandler<PreSolveEvent>> preSolveProvider;
    private ComponentMapper<OnBeginContact> beginContactMapper;
    private Function<SimpleContactEvent, ContactEventHandler<SimpleContactEvent>> beginContactProvider;
    private ComponentMapper<OnEndContact> endContactMapper;
    private Function<SimpleContactEvent, ContactEventHandler<SimpleContactEvent>> endContactProvider;
    private ComponentMapper<PostSolveContact> postSolveContactMapper;
    private Function<PostSolveEvent, ContactEventHandler<PostSolveEvent>> postSolveProvider;
    
    private EntitySubscription worlds;
    private EntitySubscription bodies;
    private EntitySubscription bodiedActors;
    
    private final ObjectIntMap<Body> bodyToEntity;
    private final PreSolveEventActionPool preSolvePool;
    private final SimpleContactEventActionPool contactPool;
    private final PostSolveEventActionPool postSolvePool;
    private final Array<Action<?>> actions;

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
        preSolvePool = new PreSolveEventActionPool();
        contactPool = new SimpleContactEventActionPool();
        postSolvePool = new PostSolveEventActionPool();
        actions = new Array<>();
    }
    
    @Override
    protected void initialize() {
        preSolveProvider = e -> preSolveContactMapper.get(e.getSelfId());
        beginContactProvider = e -> beginContactMapper.get(e.getSelfId());
        endContactProvider = e -> endContactMapper.get(e.getSelfId());
        postSolveProvider = e -> postSolveContactMapper.get(e.getSelfId());
        
        AspectSubscriptionManager subscriptionManager = world.getAspectSubscriptionManager();
        
        worlds = subscriptionManager.get(Aspect.all(Box2DWorld.class));
        SubscriptionListener worldsListener = new SubscriptionListener() {
            @Override
            public void removed(IntBag entities) {
                ArtemisCollections.forEach(entities, id -> worldMapper.get(id).get().setContactListener(null));
            }
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
        while (accumulator >= stepTime) {
            for (int index = 0; index < worldsBag.size(); index++) {
                Box2DWorld world = worldMapper.get(worldIds[index]);
                world.step(stepTime, velocityIterations, positionIterations);
            }

            Iterator<Action<?>> actionsIterator = actions.iterator();
            while (actionsIterator.hasNext()) {
                Action<?> action = actionsIterator.next();
                if (action.act(stepTime)) {
                    action.free();
                    actionsIterator.remove();
                }
            }
            
            accumulator -= stepTime;
        }
        
        ArtemisCollections.forEach(bodiedActors.getEntities(), id -> {
            BodyComponent body = bodyMapper.get(id);
            ActorComponent actor = actorMapper.get(id);
            
            Vector2 bodyPosition = body.getPosition();
            actor.setPosition(bodyPosition.x - (actor.getWidth() / 2), bodyPosition.y - (actor.getHeight() / 2));
            actor.setRotation(body.getRotation());
        });
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
            schedulePreSolveEvent(preSolveProvider, entityA, fixtureA, entityB, fixtureB, contact, oldManifold);
        }
        if (entityB != -1 && preSolveContactMapper.has(entityB)) {
            schedulePreSolveEvent(preSolveProvider, entityB, fixtureB, entityA, fixtureA, contact, oldManifold);
        }
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        int entityA = getEntityForBody(fixtureA.getBody());
        Fixture fixtureB = contact.getFixtureB();
        int entityB = getEntityForBody(fixtureB.getBody());
        
        if (entityA != -1 && beginContactMapper.has(entityA)) {
            scheduleSimpleContactEvent(beginContactProvider, entityA, fixtureA, entityB, fixtureB, contact);
        }
        if (entityB != -1 && beginContactMapper.has(entityB)) {
            scheduleSimpleContactEvent(beginContactProvider, entityB, fixtureB, entityA, fixtureA, contact);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        int entityA = getEntityForBody(fixtureA.getBody());
        Fixture fixtureB = contact.getFixtureB();
        int entityB = getEntityForBody(fixtureB.getBody());
        
        if (entityA != -1 && endContactMapper.has(entityA)) {
            scheduleSimpleContactEvent(endContactProvider, entityA, fixtureA, entityB, fixtureB, contact);
        }
        if (entityB != -1 && endContactMapper.has(entityB)) {
            scheduleSimpleContactEvent(endContactProvider, entityB, fixtureB, entityA, fixtureA, contact);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        Fixture fixtureA = contact.getFixtureA();
        int entityA = getEntityForBody(fixtureA.getBody());
        Fixture fixtureB = contact.getFixtureB();
        int entityB = getEntityForBody(fixtureB.getBody());
        
        if (entityA != -1 && postSolveContactMapper.has(entityA)) {
            schedulePostSolveEvent(postSolveProvider, entityA, fixtureA, entityB, fixtureB, contact, impulse);
        }
        if (entityB != -1 && postSolveContactMapper.has(entityB)) {
            schedulePostSolveEvent(postSolveProvider, entityB, fixtureB, entityA, fixtureA, contact, impulse);
        }
    }

    private void schedulePreSolveEvent(Function<PreSolveEvent, ContactEventHandler<PreSolveEvent>> handlerProvider,
                                       int selfId, Fixture selfFixture, int otherId, Fixture otherFixture, Contact contact, Manifold oldManifold) {
        ContactEventAction<PreSolveEvent> action = preSolvePool.obtain();
        action.set(handlerProvider, obtainPreSolveEvent(selfId, selfFixture, otherId, otherFixture, contact, oldManifold));
        actions.add(action);
    }

    private void scheduleSimpleContactEvent(Function<SimpleContactEvent, ContactEventHandler<SimpleContactEvent>> handlerProvider,
                                           int selfId, Fixture selfFixture, int otherId, Fixture otherFixture, Contact contact) {
        ContactEventAction<SimpleContactEvent> action = contactPool.obtain();
        action.set(handlerProvider, obtainSimpleContactEvent(selfId, selfFixture, otherId, otherFixture, contact));
        actions.add(action);
    }

    private void schedulePostSolveEvent(Function<PostSolveEvent, ContactEventHandler<PostSolveEvent>> handlerProvider,
                                       int selfId, Fixture selfFixture, int otherId, Fixture otherFixture, Contact contact, ContactImpulse impulse) {
        ContactEventAction<PostSolveEvent> action = postSolvePool.obtain();
        action.set(handlerProvider, obtainPostSolveEvent(selfId, selfFixture, otherId, otherFixture, contact, impulse));
        actions.add(action);
    }
    
    private PreSolveEvent obtainPreSolveEvent(int selfId, Fixture selfFixture, int otherId, Fixture otherFixture, Contact contact, Manifold oldManifold) {
        return PooledPools.obtain(PreSolveEvent.class).set(selfId, selfFixture, otherId, otherFixture, contact, oldManifold);
    }

    private SimpleContactEvent obtainSimpleContactEvent(int selfId, Fixture selfFixture, int otherId, Fixture otherFixture, Contact contact) {
        return PooledPools.obtain(SimpleContactEvent.class).set(selfId, selfFixture, otherId, otherFixture, contact);
    }
    
    private PostSolveEvent obtainPostSolveEvent(int selfId, Fixture selfFixture, int otherId, Fixture otherFixture, Contact contact, ContactImpulse impulse) {
        return PooledPools.obtain(PostSolveEvent.class).set(selfId, selfFixture, otherId, otherFixture, contact, impulse);
    }
    
    private class PreSolveEventActionPool extends PooledPool<ContactEventAction<PreSolveEvent>> {
        
        public PreSolveEventActionPool() {
            super(32, 100);
        }

        @Override
        protected ContactEventAction<PreSolveEvent> newObject() {
            return new ContactEventAction<PreSolveEvent>();
        }
        
    }
    
    private class SimpleContactEventActionPool extends PooledPool<ContactEventAction<SimpleContactEvent>> {
        
        public SimpleContactEventActionPool() {
            super(32, 100);
        }

        @Override
        protected ContactEventAction<SimpleContactEvent> newObject() {
            return new ContactEventAction<SimpleContactEvent>();
        }
        
    }
    
    private class PostSolveEventActionPool extends PooledPool<ContactEventAction<PostSolveEvent>> {
        
        public PostSolveEventActionPool() {
            super(32, 100);
        }

        @Override
        protected ContactEventAction<PostSolveEvent> newObject() {
            return new ContactEventAction<PostSolveEvent>();
        }
        
    }
    
    private class ContactEventAction<E extends ContactEvent<E>> extends AbstractAction<ContactEventAction<E>> {
        
        private Function<E, ContactEventHandler<E>> handlerProvider;
        private E event;
        
        public ContactEventAction<E> set(Function<E, ContactEventHandler<E>> handlerProvider, E event) {
            this.handlerProvider = handlerProvider;
            this.event = event;
            return this;
        }

        @Override
        public boolean act(float deltaTime) {
            handlerProvider.apply(event).accept(event);
            return true;
        }
        
        @Override
        public void reset() {
            super.reset();
            handlerProvider = null;
            event = null;
        }
        
    }
    
}
