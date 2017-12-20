package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

abstract class AbstractBodyBuilderBase<BuilderType> implements BodyBuilderBase<BuilderType> {
    
    BuilderType builder;

    protected final BodyDef bodyDefinition;
    protected short categoryBits;
    protected boolean hasCategoryBits;
    protected short maskBits;
    protected boolean hasMaskBits;
    protected short groupIndex;
    protected boolean hasGroupIndex;
    
    protected AbstractBodyBuilderBase(BodyDef bodyDefinition) {
        this.bodyDefinition = bodyDefinition;
    }
    
    @Override
    public BuilderType type(BodyType type) {
        bodyDefinition.type = type;
        return builder;
    }
    
    @Override
    public BuilderType at(float x, float y) {
        bodyDefinition.position.set(x, y);
        return builder;
    }
    
    @Override
    public BuilderType rotatedByRadians(float angle) {
        bodyDefinition.angle = angle;
        return builder;
    }
    
    @Override
    public BuilderType withLinearVelocity(float x, float y) {
        bodyDefinition.linearVelocity.set(x, y);
        return builder;
    }
    
    @Override
    public BuilderType withAngularVelocityInRadians(float velocity) {
        bodyDefinition.angularVelocity = velocity;
        return builder;
    }
    
    @Override
    public BuilderType withAngularVelocityInDegrees(float velocity) {
        bodyDefinition.angularVelocity = velocity * MathUtils.degreesToRadians;
        return builder;
    }
    
    @Override
    public BuilderType withLinearDamping(float dampingFactor) {
        bodyDefinition.linearDamping = dampingFactor;
        return builder;
    }
    
    @Override
    public BuilderType withAngularDamping(float dampingFactor) {
        bodyDefinition.angularDamping = dampingFactor;
        return builder;
    }
    
    @Override
    public BuilderType allowingSleep(boolean allowSleep) {
        bodyDefinition.allowSleep = allowSleep;
        return builder;
    }
    
    @Override
    public BuilderType isAwake(boolean isAwake) {
        bodyDefinition.awake = isAwake;
        return builder;
    }
    
    @Override
    public BuilderType withFixedRotation(boolean fixedRotation) {
        bodyDefinition.fixedRotation = fixedRotation;
        return builder;
    }
    
    @Override
    public BuilderType asBullet(boolean isBullet) {
        bodyDefinition.bullet = isBullet;
        return builder;
    }
    
    @Override
    public BuilderType active(boolean isActive) {
        bodyDefinition.active = isActive;
        return builder;
    }
    
    @Override
    public BuilderType withGravityScale(float gravityScale) {
        bodyDefinition.gravityScale = gravityScale;
        return builder;
    }

    @Override
    public BuilderType withCategoryBits(short categoryBits) {
        this.categoryBits = categoryBits;
        hasCategoryBits = true;
        return builder;
    }

    @Override
    public BuilderType withMaskBits(short maskBits) {
        this.maskBits = maskBits;
        hasMaskBits = true;
        return builder;
    }

    @Override
    public BuilderType withGroupIndex(short groupIndex) {
        this.groupIndex = groupIndex;
        hasGroupIndex = true;
        return builder;
    }
    
}
