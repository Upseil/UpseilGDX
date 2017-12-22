package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.upseil.gdx.box2d.builder.base.AbstractBodyDefinitionBuilderBase;
import com.upseil.gdx.box2d.util.Bodies;

public class BodyDefinitionBuilder extends AbstractBodyDefinitionBuilderBase<BodyDef> {

    public BodyDefinitionBuilder() {
        this(Bodies.DefaultBodyDefinition);
    }
    
    public BodyDefinitionBuilder(BodyDef template) {
        super(Bodies.copy(template));
    }

    @Override
    public BodyDef build() {
        return Bodies.copy(bodyDefinition);
    }
    
//- Overriding members for concrete return type -----------------------------------------

    @Override
    public BodyDefinitionBuilder type(BodyType type) {
        super.type(type);
        return this;
    }

    @Override
    public BodyDefinitionBuilder withStaticBody() {
        super.withStaticBody();
        return this;
    }

    @Override
    public BodyDefinitionBuilder withKinematicBody() {
        super.withKinematicBody();
        return this;
    }

    @Override
    public BodyDefinitionBuilder withDynamicBody() {
        super.withDynamicBody();
        return this;
    }

    @Override
    public BodyDefinitionBuilder at(float x, float y) {
        super.at(x, y);
        return this;
    }

    @Override
    public BodyDefinitionBuilder at(Vector2 position) {
        super.at(position);
        return this;
    }

    @Override
    public BodyDefinitionBuilder rotatedByRadians(float angle) {
        super.rotatedByRadians(angle);
        return this;
    }

    @Override
    public BodyDefinitionBuilder rotatedByDegrees(float angle) {
        super.rotatedByDegrees(angle);
        return this;
    }

    @Override
    public BodyDefinitionBuilder withLinearVelocity(float x, float y) {
        super.withLinearVelocity(x, y);
        return this;
    }

    @Override
    public BodyDefinitionBuilder withLinearVelocity(Vector2 velocity) {
        super.withLinearVelocity(velocity);
        return this;
    }

    @Override
    public BodyDefinitionBuilder withAngularVelocityInRadians(float velocity) {
        super.withAngularVelocityInRadians(velocity);
        return this;
    }

    @Override
    public BodyDefinitionBuilder withAngularVelocityInDegrees(float velocity) {
        super.withAngularVelocityInDegrees(velocity);
        return this;
    }

    @Override
    public BodyDefinitionBuilder withLinearDamping(float dampingFactor) {
        super.withLinearDamping(dampingFactor);
        return this;
    }

    @Override
    public BodyDefinitionBuilder withAngularDamping(float dampingFactor) {
        super.withAngularDamping(dampingFactor);
        return this;
    }

    @Override
    public BodyDefinitionBuilder allowingSleep(boolean allowSleep) {
        super.allowingSleep(allowSleep);
        return this;
    }

    @Override
    public BodyDefinitionBuilder allowingSleep() {
        super.allowingSleep();
        return this;
    }

    @Override
    public BodyDefinitionBuilder prohibtingSleep() {
        super.prohibtingSleep();
        return this;
    }

    @Override
    public BodyDefinitionBuilder isAwake(boolean isAwake) {
        super.isAwake(isAwake);
        return this;
    }

    @Override
    public BodyDefinitionBuilder awake() {
        super.awake();
        return this;
    }

    @Override
    public BodyDefinitionBuilder sleeping() {
        super.sleeping();
        return this;
    }

    @Override
    public BodyDefinitionBuilder withFixedRotation(boolean fixedRotation) {
        super.withFixedRotation(fixedRotation);
        return this;
    }

    @Override
    public BodyDefinitionBuilder withFixedRotation() {
        super.withFixedRotation();
        return this;
    }

    @Override
    public BodyDefinitionBuilder withoutFixedRotation() {
        super.withoutFixedRotation();
        return this;
    }

    @Override
    public BodyDefinitionBuilder asBullet(boolean isBullet) {
        super.asBullet(isBullet);
        return this;
    }

    @Override
    public BodyDefinitionBuilder asBullet() {
        super.asBullet();
        return this;
    }

    @Override
    public BodyDefinitionBuilder notAsBullet() {
        super.notAsBullet();
        return this;
    }

    @Override
    public BodyDefinitionBuilder active(boolean isActive) {
        super.active(isActive);
        return this;
    }

    @Override
    public BodyDefinitionBuilder active() {
        super.active();
        return this;
    }

    @Override
    public BodyDefinitionBuilder inactive() {
        super.inactive();
        return this;
    }

    @Override
    public BodyDefinitionBuilder withGravityScale(float gravityScale) {
        super.withGravityScale(gravityScale);
        return this;
    }

    @Override
    public BodyDefinitionBuilder withZeroGravity() {
        super.withZeroGravity();
        return this;
    }
    
}
