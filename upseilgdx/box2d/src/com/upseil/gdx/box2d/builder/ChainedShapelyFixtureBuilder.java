package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.upseil.gdx.box2d.builder.base.ChainedFixtureBuilderBase;
import com.upseil.gdx.box2d.builder.shape.ShapeBuilder;

public class ChainedShapelyFixtureBuilder extends ShapelyFixtureBuilder implements ChainedFixtureBuilderBase<FixtureDef, BodyBuilder> {
    
    private final BodyBuilder parent;

    public ChainedShapelyFixtureBuilder(FixtureDef fixtureDefinition, ShapeBuilder<?> shape, BodyBuilder parent) {
        super(fixtureDefinition, shape);
        this.parent = parent;
    }

    @Override
    public BodyBuilder endFixture() {
        return parent;
    }

//- Overriding members for concrete return type -----------------------------------------

    @Override
    public ChainedShapelyFixtureBuilder withFriction(float friction) {
        super.withFriction(friction);
        return this;
    }

    @Override
    public ChainedShapelyFixtureBuilder withRestitution(float restitution) {
        super.withRestitution(restitution);
        return this;
    }

    @Override
    public ChainedShapelyFixtureBuilder withDensity(float density) {
        super.withDensity(density);
        return this;
    }

    @Override
    public ChainedShapelyFixtureBuilder asSensor(boolean isSensor) {
        super.asSensor(isSensor);
        return this;
    }

    @Override
    public ChainedShapelyFixtureBuilder asSensor() {
        super.asSensor();
        return this;
    }

    @Override
    public ChainedShapelyFixtureBuilder notAsSensor() {
        super.notAsSensor();
        return this;
    }

    @Override
    public ChainedShapelyFixtureBuilder withCategoryBits(short categoryBits) {
        super.withCategoryBits(categoryBits);
        return this;
    }

    @Override
    public ChainedShapelyFixtureBuilder withMaskBits(short maskBits) {
        super.withMaskBits(maskBits);
        return this;
    }

    @Override
    public ChainedShapelyFixtureBuilder withGroupIndex(short groupIndex) {
        super.withGroupIndex(groupIndex);
        return this;
    }

    @Override
    public ChainedShapelyFixtureBuilder withFilter(Filter filter) {
        super.withFilter(filter);
        return this;
    }

    @Override
    public ChainedShapelyFixtureBuilder withFilter(short categoryBits, short maskBits, short groupIndex) {
        super.withFilter(categoryBits, maskBits, groupIndex);
        return this;
    }
    
}
