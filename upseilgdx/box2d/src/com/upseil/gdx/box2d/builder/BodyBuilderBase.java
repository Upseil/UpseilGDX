package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;

interface BodyBuilderBase<BuilderType> {
    
    BuilderType type(BodyType type);
    default BuilderType withStaticBody() {
        return type(BodyType.StaticBody);
    }
    default BuilderType withKinematicBody() {
        return type(BodyType.KinematicBody);
    }
    default BuilderType withDynamicBody() {
        return type(BodyType.DynamicBody);
    }
    
    BuilderType at(float x, float y);
    default BuilderType at(Vector2 position) {
        return at(position.x, position.y);
    }
    
    BuilderType rotatedByRadians(float angle);
    default BuilderType rotatedByDegrees(float angle) {
        return rotatedByRadians(angle * MathUtils.degreesToRadians);
    }
    
    BuilderType withLinearVelocity(float x, float y);
    default BuilderType withLinearVelocity(Vector2 velocity) {
        return withLinearVelocity(velocity.x, velocity.y);
    }
    
    BuilderType withAngularVelocityInRadians(float velocity);
    BuilderType withAngularVelocityInDegrees(float velocity);
    
    BuilderType withLinearDamping(float dampingFactor);
    BuilderType withAngularDamping(float dampingFactor);
    
    BuilderType allowingSleep(boolean allowSleep);
    default BuilderType allowingSleep() {
        return allowingSleep(true);
    }
    default BuilderType prohibtingSleep() {
        return allowingSleep(false);
    }
    
    BuilderType isAwake(boolean isAwake);
    default BuilderType awake() {
        return isAwake(true);
    }
    default BuilderType sleeping() {
        return isAwake(false);
    }
    
    BuilderType withFixedRotation(boolean fixedRotation);
    default BuilderType withFixedRotation() {
        return withFixedRotation(true);
    }
    default BuilderType withoutFixedRotation() {
        return withFixedRotation(false);
    }
    
    BuilderType asBullet(boolean isBullet);
    default BuilderType asBullet() {
        return asBullet(true);
    }
    default BuilderType notAsBullet() {
        return asBullet(false);
    }
    
    BuilderType active(boolean isActive);
    default BuilderType active() {
        return active(true);
    }
    default BuilderType inactive() {
        return active(false);
    }
    
    BuilderType withGravityScale(float gravityScale);
    default BuilderType withZeroGravity() {
        return withGravityScale(0);
    }
    
    BuilderType withCategoryBits(short categoryBits);
    BuilderType withMaskBits(short maskBits);
    BuilderType withGroupIndex(short groupIndex);
    
    default BuilderType withFilter(Filter filter) {
        withCategoryBits(filter.categoryBits);
        withMaskBits(filter.maskBits);
        return withGroupIndex(filter.groupIndex);
    }
    
    default BuilderType withFilter(short categoryBits, short maskBits, short groupIndex) {
        withCategoryBits(categoryBits);
        withMaskBits(maskBits);
        return withGroupIndex(groupIndex);
    }
    
}