package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;

abstract class AbstractBodyBuilderBase<BuilderType> implements BodyBuilderBase<BuilderType> {
    
    protected final BodyDef bodyDefinition;
    
    protected short categoryBits;
    protected boolean hasCategoryBits;

    protected short maskBits;
    protected boolean hasMaskBits;
    
    protected short groupIndex;
    protected boolean hasGroupIndex;
    
    private BuilderType builder;
    
    protected AbstractBodyBuilderBase(BodyDef bodyDefinition) {
        this.bodyDefinition = bodyDefinition;
    }
    
    protected void setBuilder(BuilderType builder) {
        this.builder = builder;
    }
    
    @Override
    public BuilderType type(BodyType type) {
        bodyDefinition.type = type;
        return builder;
    }
    
    @Override
    public BuilderType position(float x, float y) {
        bodyDefinition.position.set(x, y);
        return builder;
    }
    
    @Override
    public BuilderType angleInRadians(float angle) {
        bodyDefinition.angle = angle;
        return builder;
    }
    
    @Override
    public BuilderType angleInDegrees(float angle) {
        bodyDefinition.angle = angle * MathUtils.degreesToRadians;
        return builder;
    }
    
    @Override
    public BuilderType linearVelocity(float x, float y) {
        bodyDefinition.linearVelocity.set(x, y);
        return builder;
    }
    
    @Override
    public BuilderType angularVelocityInRadians(float velocity) {
        bodyDefinition.angularVelocity = velocity;
        return builder;
    }
    
    @Override
    public BuilderType angularVelocityInDegrees(float velocity) {
        bodyDefinition.angularVelocity = velocity * MathUtils.degreesToRadians;
        return builder;
    }
    
    @Override
    public BuilderType linearDamping(float dampingFactor) {
        bodyDefinition.linearDamping = dampingFactor;
        return builder;
    }
    
    @Override
    public BuilderType angularDamping(float dampingFactor) {
        bodyDefinition.angularDamping = dampingFactor;
        return builder;
    }
    
    @Override
    public BuilderType allowSleep(boolean allowSleep) {
        bodyDefinition.allowSleep = allowSleep;
        return builder;
    }
    
    @Override
    public BuilderType isAwake(boolean isAwake) {
        bodyDefinition.awake = isAwake;
        return builder;
    }
    
    @Override
    public BuilderType fixedRotation(boolean fixedRotation) {
        bodyDefinition.fixedRotation = fixedRotation;
        return builder;
    }
    
    @Override
    public BuilderType isBullet(boolean isBullet) {
        bodyDefinition.bullet = isBullet;
        return builder;
    }
    
    @Override
    public BuilderType isActive(boolean isActive) {
        bodyDefinition.active = isActive;
        return builder;
    }
    
    @Override
    public BuilderType gravityScale(float gravityScale) {
        bodyDefinition.gravityScale = gravityScale;
        return builder;
    }

    @Override
    public BuilderType categoryBits(short categoryBits) {
        this.categoryBits = categoryBits;
        hasCategoryBits = true;
        return builder;
    }

    @Override
    public BuilderType maskBits(short maskBits) {
        this.maskBits = maskBits;
        hasMaskBits = true;
        return builder;
    }

    @Override
    public BuilderType groupIndex(short groupIndex) {
        this.groupIndex = groupIndex;
        hasGroupIndex = true;
        return builder;
    }

    @Override
    public BuilderType filter(Filter filter) {
        return filter(filter.categoryBits, filter.maskBits, filter.groupIndex);
    }

    @Override
    public BuilderType filter(short categoryBits, short maskBits, short groupIndex) {
        categoryBits(categoryBits);
        maskBits(maskBits);
        groupIndex(groupIndex);
        return builder;
    }
    
}
