package com.upseil.gdx.box2d.builder.base;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.upseil.gdx.util.builder.Builder;

public interface BodyDefinitionBuilderBase<T> extends Builder<T> {
    
    BodyDefinitionBuilderBase<T> type(BodyType type);
    default BodyDefinitionBuilderBase<T> withStaticBody() {
        return type(BodyType.StaticBody);
    }
    default BodyDefinitionBuilderBase<T> withKinematicBody() {
        return type(BodyType.KinematicBody);
    }
    default BodyDefinitionBuilderBase<T> withDynamicBody() {
        return type(BodyType.DynamicBody);
    }
    
    BodyDefinitionBuilderBase<T> at(float x, float y);
    default BodyDefinitionBuilderBase<T> at(Vector2 position) {
        return at(position.x, position.y);
    }
    
    BodyDefinitionBuilderBase<T> rotatedByRadians(float angle);
    default BodyDefinitionBuilderBase<T> rotatedByDegrees(float angle) {
        return rotatedByRadians(angle * MathUtils.degreesToRadians);
    }
    
    BodyDefinitionBuilderBase<T> withLinearVelocity(float x, float y);
    default BodyDefinitionBuilderBase<T> withLinearVelocity(Vector2 velocity) {
        return withLinearVelocity(velocity.x, velocity.y);
    }
    
    BodyDefinitionBuilderBase<T> withAngularVelocityInRadians(float velocity);
    default BodyDefinitionBuilderBase<T> withAngularVelocityInDegrees(float velocity) {
        return withAngularVelocityInRadians(velocity * MathUtils.degreesToRadians);
    }
    
    BodyDefinitionBuilderBase<T> withLinearDamping(float dampingFactor);
    BodyDefinitionBuilderBase<T> withAngularDamping(float dampingFactor);
    
    BodyDefinitionBuilderBase<T> allowingSleep(boolean allowSleep);
    default BodyDefinitionBuilderBase<T> allowingSleep() {
        return allowingSleep(true);
    }
    default BodyDefinitionBuilderBase<T> prohibtingSleep() {
        return allowingSleep(false);
    }
    
    BodyDefinitionBuilderBase<T> isAwake(boolean isAwake);
    default BodyDefinitionBuilderBase<T> awake() {
        return isAwake(true);
    }
    default BodyDefinitionBuilderBase<T> sleeping() {
        return isAwake(false);
    }
    
    BodyDefinitionBuilderBase<T> withFixedRotation(boolean fixedRotation);
    default BodyDefinitionBuilderBase<T> withFixedRotation() {
        return withFixedRotation(true);
    }
    default BodyDefinitionBuilderBase<T> withoutFixedRotation() {
        return withFixedRotation(false);
    }
    
    BodyDefinitionBuilderBase<T> asBullet(boolean isBullet);
    default BodyDefinitionBuilderBase<T> asBullet() {
        return asBullet(true);
    }
    default BodyDefinitionBuilderBase<T> notAsBullet() {
        return asBullet(false);
    }
    
    BodyDefinitionBuilderBase<T> active(boolean isActive);
    default BodyDefinitionBuilderBase<T> active() {
        return active(true);
    }
    default BodyDefinitionBuilderBase<T> inactive() {
        return active(false);
    }
    
    BodyDefinitionBuilderBase<T> withGravityScale(float gravityScale);
    default BodyDefinitionBuilderBase<T> withZeroGravity() {
        return withGravityScale(0);
    }
    
//- Utility methods ---------------------------------------------------------------------
    
    float getAngleInRadians();
    default float getAngleInDegrees() {
        return getAngleInRadians() * MathUtils.radiansToDegrees;
    }
    
}