package com.upseil.gdx.box2d.builder.base;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public abstract class AbstractBodyBuilderBase<T, F> extends AbstractBodyDefinitionBuilderBase<T> implements BodyBuilderBase<T, F> {
    
    protected final Rectangle bounds;
    
    protected short categoryBits;
    protected boolean hasCategoryBits;
    protected short maskBits;
    protected boolean hasMaskBits;
    protected short groupIndex;
    protected boolean hasGroupIndex;

    public AbstractBodyBuilderBase(BodyDef bodyDefinition) {
        super(bodyDefinition);
        bounds = new Rectangle();
    }

    @Override
    public BodyBuilderBase<T, F> withCategoryBits(short categoryBits) {
        this.categoryBits = categoryBits;
        hasCategoryBits = true;
        changed = true;
        return this;
    }

    @Override
    public BodyBuilderBase<T, F> withMaskBits(short maskBits) {
        this.maskBits = maskBits;
        hasMaskBits = true;
        changed = true;
        return this;
    }

    @Override
    public BodyBuilderBase<T, F> withGroupIndex(short groupIndex) {
        this.groupIndex = groupIndex;
        hasGroupIndex = true;
        changed = true;
        return this;
    }
    
//- Utility methods ---------------------------------------------------------------------

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public boolean hasCategoryBits() {
        return hasCategoryBits;
    }

    @Override
    public boolean hasMaskBits() {
        return hasMaskBits;
    }

    @Override
    public boolean hasGroupIndex() {
        return hasGroupIndex;
    }
    
    protected void applyFilter(FixtureBuilderBase<?> fixtureBuilder) {
        if (hasCategoryBits() && !fixtureBuilder.hasCategoryBits()) {
            fixtureBuilder.withCategoryBits(categoryBits);
        }
        if (hasMaskBits() && !fixtureBuilder.hasMaskBits()) {
            fixtureBuilder.withMaskBits(maskBits);
        }
        if (hasGroupIndex() && !fixtureBuilder.hasGroupIndex()) {
            fixtureBuilder.withGroupIndex(groupIndex);
        }
    }
    
//- Overriding members for concrete return type -----------------------------------------

    @Override
    public BodyBuilderBase<T, F> type(BodyType type) {
        super.type(type);
        return this;
    }

    @Override
    public BodyBuilderBase<T, F> at(float x, float y) {
        super.at(x, y);
        return this;
    }

    @Override
    public BodyBuilderBase<T, F> rotatedByRadians(float angle) {
        super.rotatedByRadians(angle);
        return this;
    }

    @Override
    public BodyBuilderBase<T, F> withLinearVelocity(float x, float y) {
        super.withLinearVelocity(x, y);
        return this;
    }

    @Override
    public BodyBuilderBase<T, F> withAngularVelocityInRadians(float velocity) {
        super.withAngularVelocityInRadians(velocity);
        return this;
    }

    @Override
    public BodyBuilderBase<T, F> withLinearDamping(float dampingFactor) {
        super.withLinearDamping(dampingFactor);
        return this;
    }

    @Override
    public BodyBuilderBase<T, F> withAngularDamping(float dampingFactor) {
        super.withAngularDamping(dampingFactor);
        return this;
    }

    @Override
    public BodyBuilderBase<T, F> allowingSleep(boolean allowSleep) {
        super.allowingSleep(allowSleep);
        return this;
    }

    @Override
    public BodyBuilderBase<T, F> isAwake(boolean isAwake) {
        super.isAwake(isAwake);
        return this;
    }

    @Override
    public BodyBuilderBase<T, F> withFixedRotation(boolean fixedRotation) {
        super.withFixedRotation(fixedRotation);
        return this;
    }

    @Override
    public BodyBuilderBase<T, F> asBullet(boolean isBullet) {
        super.asBullet(isBullet);
        return this;
    }

    @Override
    public BodyBuilderBase<T, F> active(boolean isActive) {
        super.active(isActive);
        return this;
    }

    @Override
    public BodyBuilderBase<T, F> withGravityScale(float gravityScale) {
        super.withGravityScale(gravityScale);
        return this;
    }
    
}
