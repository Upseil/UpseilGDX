package com.upseil.gdx.box2d.system;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.math.Vector2;
import com.upseil.gdx.artemis.component.ActorComponent;
import com.upseil.gdx.box2d.component.BodyComponent;
import com.upseil.gdx.box2d.component.Box2DWorld;

public class PhysicsSystem extends BaseSystem {

    private ComponentMapper<Box2DWorld> worldMapper;
    private ComponentMapper<BodyComponent> bodyMapper;
    private ComponentMapper<ActorComponent> actorMapper;
    
    private EntitySubscription worlds;
    private EntitySubscription bodiedActors;

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
    }
    
    @Override
    protected void initialize() {
        AspectSubscriptionManager subscriptionManager = world.getAspectSubscriptionManager();
        worlds = subscriptionManager.get(Aspect.all(Box2DWorld.class));
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
        
        IntBag bodiedActorsBag = bodiedActors.getEntities();
        int[] bodiedActorIds = bodiedActorsBag.getData();
        for (int index = 0; index < bodiedActorsBag.size(); index++) {
            int id = bodiedActorIds[index];
            BodyComponent body = bodyMapper.get(id);
            ActorComponent actor = actorMapper.get(id);
            
            Vector2 bodyPosition = body.getPosition();
            actor.setPosition(bodyPosition.x - (actor.getWidth() / 2), bodyPosition.y - (actor.getHeight() / 2));
            actor.setRotation(body.getRotation());
        }
    }
    
}
