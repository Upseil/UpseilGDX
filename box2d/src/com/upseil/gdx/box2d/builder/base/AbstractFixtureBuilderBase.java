package com.upseil.gdx.box2d.builder.base;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.upseil.gdx.box2d.builder.shape.ShapeBuilder;

public abstract class AbstractFixtureBuilderBase<T> implements FixtureBuilderBase<T> {
    
    protected final FixtureDef fixtureDefinition;
    protected final ShapeBuilder<?> shape;
    
    protected boolean hasCategoryBits;
    protected boolean hasMaskBits;
    protected boolean hasGroupIndex;
    
    protected boolean changed;

    protected AbstractFixtureBuilderBase(FixtureDef fixtureDefinition, ShapeBuilder<?> shape) {
        this.fixtureDefinition = fixtureDefinition;
        this.shape = shape;
    }
    
    @Override
    public FixtureBuilderBase<T> withFriction(float friction) {
        fixtureDefinition.friction = friction;
        changed = true;
        return this;
    }
    
    @Override
    public FixtureBuilderBase<T> withRestitution(float restitution) {
        fixtureDefinition.restitution = restitution;
        changed = true;
        return this;
    }
    
    @Override
    public FixtureBuilderBase<T> withDensity(float density) {
        fixtureDefinition.density = density;
        changed = true;
        return this;
    }
    
    @Override
    public FixtureBuilderBase<T> asSensor(boolean isSensor) {
        fixtureDefinition.isSensor = isSensor;
        changed = true;
        return this;
    }
    
    @Override
    public FixtureBuilderBase<T> withCategoryBits(short categoryBits) {
        fixtureDefinition.filter.categoryBits = categoryBits;
        hasCategoryBits = true;
        changed = true;
        return this;
    }
    
    @Override
    public FixtureBuilderBase<T> withMaskBits(short maskBits) {
        fixtureDefinition.filter.maskBits = maskBits;
        hasMaskBits = true;
        changed = true;
        return this;
    }
    
    @Override
    public FixtureBuilderBase<T> withGroupIndex(short groupIndex) {
        fixtureDefinition.filter.groupIndex = groupIndex;
        hasGroupIndex = true;
        changed = true;
        return this;
    }
    
    @Override
    public void dispose() {
        shape.dispose();
    }
    
//- Utility methods ---------------------------------------------------------------------
    
    @Override
    public Rectangle getBounds() {
        return shape.getBounds();
    }
    
    @Override
    public float getAngle() {
        return shape.getAngle();
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
    
}
