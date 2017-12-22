package com.upseil.gdx.box2d.builder.base;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public abstract class AbstractBodyDefinitionBuilderBase<T> implements BodyDefinitionBuilderBase<T> {

    protected final BodyDef bodyDefinition;
    protected boolean changed;
    
    public AbstractBodyDefinitionBuilderBase(BodyDef bodyDefinition) {
        this.bodyDefinition = bodyDefinition;
    }
    
    @Override
    public BodyDefinitionBuilderBase<T> type(BodyType type) {
        bodyDefinition.type = type;
        changed = true;
        return this;
    }
    
    @Override
    public BodyDefinitionBuilderBase<T> at(float x, float y) {
        bodyDefinition.position.set(x, y);
        changed = true;
        return this;
    }
    
    @Override
    public BodyDefinitionBuilderBase<T> rotatedByRadians(float angle) {
        bodyDefinition.angle = angle;
        changed = true;
        return this;
    }
    
    @Override
    public BodyDefinitionBuilderBase<T> withLinearVelocity(float x, float y) {
        bodyDefinition.linearVelocity.set(x, y);
        changed = true;
        return this;
    }
    
    @Override
    public BodyDefinitionBuilderBase<T> withAngularVelocityInRadians(float velocity) {
        bodyDefinition.angularVelocity = velocity;
        changed = true;
        return this;
    }
    
    @Override
    public BodyDefinitionBuilderBase<T> withLinearDamping(float dampingFactor) {
        bodyDefinition.linearDamping = dampingFactor;
        changed = true;
        return this;
    }
    
    @Override
    public BodyDefinitionBuilderBase<T> withAngularDamping(float dampingFactor) {
        bodyDefinition.angularDamping = dampingFactor;
        return this;
    }
    
    @Override
    public BodyDefinitionBuilderBase<T> allowingSleep(boolean allowSleep) {
        bodyDefinition.allowSleep = allowSleep;
        changed = true;
        return this;
    }
    
    @Override
    public BodyDefinitionBuilderBase<T> isAwake(boolean isAwake) {
        bodyDefinition.awake = isAwake;
        changed = true;
        return this;
    }
    
    @Override
    public BodyDefinitionBuilderBase<T> withFixedRotation(boolean fixedRotation) {
        bodyDefinition.fixedRotation = fixedRotation;
        changed = true;
        return this;
    }
    
    @Override
    public BodyDefinitionBuilderBase<T> asBullet(boolean isBullet) {
        bodyDefinition.bullet = isBullet;
        changed = true;
        return this;
    }
    
    @Override
    public BodyDefinitionBuilderBase<T> active(boolean isActive) {
        bodyDefinition.active = isActive;
        changed = true;
        return this;
    }
    
    @Override
    public BodyDefinitionBuilderBase<T> withGravityScale(float gravityScale) {
        bodyDefinition.gravityScale = gravityScale;
        changed = true;
        return this;
    }
    
//- Utility methods ---------------------------------------------------------------------
    
    @Override
    public float getAngleInRadians() {
        return bodyDefinition.angle;
    }
    
}
