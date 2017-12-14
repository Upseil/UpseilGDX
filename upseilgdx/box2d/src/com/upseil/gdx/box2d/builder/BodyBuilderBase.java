package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

interface BodyBuilderBase<BuilderType> {
    
    BuilderType type(BodyType type);
    
    BuilderType position(float x, float y);
    
    BuilderType angleInRadians(float angle);
    BuilderType angleInDegrees(float angle);
    
    BuilderType linearVelocity(float x, float y);
    
    BuilderType angularVelocityInRadians(float velocity);
    BuilderType angularVelocityInDegrees(float velocity);
    
    BuilderType linearDamping(float dampingFactor);
    BuilderType angularDamping(float dampingFactor);
    
    BuilderType allowSleep(boolean allowSleep);
    BuilderType isAwake(boolean isAwake);
    
    BuilderType fixedRotation(boolean fixedRotation);
    
    BuilderType isBullet(boolean isBullet);
    
    BuilderType isActive(boolean isActive);
    
    BuilderType gravityScale(float gravityScale);
    
}