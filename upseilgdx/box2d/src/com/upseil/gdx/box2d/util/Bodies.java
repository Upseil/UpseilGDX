package com.upseil.gdx.box2d.util;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Pool;
import com.upseil.gdx.box2d.builder.BodiedActorBuilder;
import com.upseil.gdx.box2d.builder.BodyBuilder;
import com.upseil.gdx.box2d.builder.BodyDefinitionBuilder;
import com.upseil.gdx.pool.PairPool;
import com.upseil.gdx.pool.PooledPair;

public final class Bodies {
    
    public static final BodyDef DefaultBodyDefinition = new BodyDef();
    public static final PairPool<Body, Actor> DefaultBodiedActorPool = new PairPool<>(4, 100);
    
    public static BodyDefinitionBuilder newBodyDefinition() {
        return newBodyDefinition(DefaultBodyDefinition);
    }

    public static BodyDefinitionBuilder newBodyDefinition(BodyDef template) {
        return new BodyDefinitionBuilder(copy(template));
    }
    
    public static BodyBuilder newBody(World world) {
        return newBody(world, DefaultBodyDefinition);
    }

    public static BodyBuilder newBody(World world, BodyDef template) {
        return new BodyBuilder(world, copy(template));
    }
    
    public static BodiedActorBuilder newBodiedActor(World world) {
        return newBodiedActor(world, null, DefaultBodiedActorPool, DefaultBodyDefinition);
    }
    
    public static BodiedActorBuilder newBodiedActor(World world, BodyDef template) {
        return newBodiedActor(world, null, DefaultBodiedActorPool, template);
    }
    
    public static BodiedActorBuilder newBodiedActor(World world, Skin skin) {
        return newBodiedActor(world, skin, DefaultBodiedActorPool, DefaultBodyDefinition);
    }
    
    public static BodiedActorBuilder newBodiedActor(World world, Skin skin, BodyDef template) {
        return newBodiedActor(world, skin, DefaultBodiedActorPool, template);
    }

    public static BodiedActorBuilder newBodiedActor(World world, Skin skin, Pool<PooledPair<Body,Actor>> pool, BodyDef template) {
        return new BodiedActorBuilder(world, skin, pool, copy(template));
    }

    public static BodyDef copy(BodyDef bodyDefinition) {
        BodyDef clone = new BodyDef();
        apply(bodyDefinition, clone);
        return clone;
    }

    public static void apply(BodyDef source, BodyDef target) {
        target.type = source.type;
        target.position.set(source.position);
        target.angle = source.angle;
        target.linearVelocity.set(source.linearVelocity);
        target.angularVelocity = source.angularVelocity;
        target.linearDamping = source.linearDamping;
        target.angularDamping = source.angularDamping;
        target.allowSleep = source.allowSleep;
        target.awake = source.awake;
        target.fixedRotation = source.fixedRotation;
        target.bullet = source.bullet;
        target.active = source.active;
        target.gravityScale = source.gravityScale;
    }
    
    private Bodies() { }
    
}
