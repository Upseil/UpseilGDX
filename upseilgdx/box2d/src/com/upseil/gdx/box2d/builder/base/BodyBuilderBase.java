package com.upseil.gdx.box2d.builder.base;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Disposable;

public interface BodyBuilderBase<T, F> extends BodyDefinitionBuilderBase<T>, Disposable {
    
    BodyBuilderBase<T, F> withCategoryBits(short categoryBits);
    BodyBuilderBase<T, F> withMaskBits(short maskBits);
    BodyBuilderBase<T, F> withGroupIndex(short groupIndex);
    
    default BodyBuilderBase<T, F> withFilter(Filter filter) {
        withCategoryBits(filter.categoryBits);
        withMaskBits(filter.maskBits);
        return withGroupIndex(filter.groupIndex);
    }
    
    default BodyBuilderBase<T, F> withFilter(short categoryBits, short maskBits, short groupIndex) {
        withCategoryBits(categoryBits);
        withMaskBits(maskBits);
        return withGroupIndex(groupIndex);
    }

    ShapelessFixtureBuilderBase<? extends ChainedFixtureBuilderBase<F, ? extends BodyBuilderBase<T, F>>> beginFixture(FixtureDef template);
    ShapelessFixtureBuilderBase<? extends ChainedFixtureBuilderBase<F, ? extends BodyBuilderBase<T, F>>> beginFixture();
    
//- Utility methods ---------------------------------------------------------------------
    
    Rectangle getBounds();

    boolean hasCategoryBits();
    boolean hasMaskBits();
    boolean hasGroupIndex();
    
//- Overriding members for concrete return type -----------------------------------------
    
    BodyBuilderBase<T, F> type(BodyType type);
    default BodyBuilderBase<T, F> withStaticBody() {
        BodyDefinitionBuilderBase.super.withStaticBody();
        return this;
    }
    default BodyBuilderBase<T, F> withKinematicBody() {
        BodyDefinitionBuilderBase.super.withKinematicBody();
        return this;
    }
    default BodyBuilderBase<T, F> withDynamicBody() {
        BodyDefinitionBuilderBase.super.withDynamicBody();
        return this;
    }
    
    BodyBuilderBase<T, F> at(float x, float y);
    default BodyBuilderBase<T, F> at(Vector2 position) {
        BodyDefinitionBuilderBase.super.at(position);
        return this;
    }
    
    BodyBuilderBase<T, F> rotatedByRadians(float angle);
    default BodyBuilderBase<T, F> rotatedByDegrees(float angle) {
        BodyDefinitionBuilderBase.super.rotatedByDegrees(angle);
        return this;
    }
    
    BodyBuilderBase<T, F> withLinearVelocity(float x, float y);
    default BodyBuilderBase<T, F> withLinearVelocity(Vector2 velocity) {
        BodyDefinitionBuilderBase.super.withLinearVelocity(velocity);
        return this;
    }
    
    BodyBuilderBase<T, F> withAngularVelocityInRadians(float velocity);
    default BodyBuilderBase<T, F> withAngularVelocityInDegrees(float velocity) {
        BodyDefinitionBuilderBase.super.withAngularVelocityInDegrees(velocity);
        return this;
    }
    
    BodyBuilderBase<T, F> withLinearDamping(float dampingFactor);
    BodyBuilderBase<T, F> withAngularDamping(float dampingFactor);
    
    BodyBuilderBase<T, F> allowingSleep(boolean allowSleep);
    default BodyBuilderBase<T, F> allowingSleep() {
        BodyDefinitionBuilderBase.super.allowingSleep();
        return this;
    }
    default BodyBuilderBase<T, F> prohibtingSleep() {
        BodyDefinitionBuilderBase.super.prohibtingSleep();
        return this;
    }
    
    BodyBuilderBase<T, F> isAwake(boolean isAwake);
    default BodyBuilderBase<T, F> awake() {
        BodyDefinitionBuilderBase.super.awake();
        return this;
    }
    default BodyBuilderBase<T, F> sleeping() {
        BodyDefinitionBuilderBase.super.sleeping();
        return this;
    }
    
    BodyBuilderBase<T, F> withFixedRotation(boolean fixedRotation);
    default BodyBuilderBase<T, F> withFixedRotation() {
        BodyDefinitionBuilderBase.super.withFixedRotation();
        return this;
    }
    default BodyBuilderBase<T, F> withoutFixedRotation() {
        BodyDefinitionBuilderBase.super.withoutFixedRotation();
        return this;
    }
    
    BodyBuilderBase<T, F> asBullet(boolean isBullet);
    default BodyBuilderBase<T, F> asBullet() {
        BodyDefinitionBuilderBase.super.asBullet();
        return this;
    }
    default BodyBuilderBase<T, F> notAsBullet() {
        BodyDefinitionBuilderBase.super.notAsBullet();
        return this;
    }
    
    BodyBuilderBase<T, F> active(boolean isActive);
    default BodyBuilderBase<T, F> active() {
        BodyDefinitionBuilderBase.super.active();
        return this;
    }
    default BodyBuilderBase<T, F> inactive() {
        BodyDefinitionBuilderBase.super.inactive();
        return this;
    }
    
    BodyBuilderBase<T, F> withGravityScale(float gravityScale);
    default BodyBuilderBase<T, F> withZeroGravity() {
        BodyDefinitionBuilderBase.super.withZeroGravity();
        return this;
    }
}
