package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.upseil.gdx.box2d.builder.base.AbstractFixtureBuilderBase;
import com.upseil.gdx.box2d.builder.shape.ShapeBuilder;
import com.upseil.gdx.box2d.util.Fixtures;

public class ShapelyFixtureBuilder extends AbstractFixtureBuilderBase<FixtureDef> {
    
    ShapelyFixtureBuilder(FixtureDef fixtureDefinition, ShapeBuilder<?> shape) {
        super(fixtureDefinition, shape);
    }

    @Override
    public FixtureDef build() {
        fixtureDefinition.shape = shape.build();
        return Fixtures.copy(fixtureDefinition);
    }
    
//- Overriding members for concrete return type -----------------------------------------

    @Override
    public ShapelyFixtureBuilder withFriction(float friction) {
        super.withFriction(friction);
        return this;
    }

    @Override
    public ShapelyFixtureBuilder withRestitution(float restitution) {
        super.withRestitution(restitution);
        return this;
    }

    @Override
    public ShapelyFixtureBuilder withDensity(float density) {
        super.withDensity(density);
        return this;
    }

    @Override
    public ShapelyFixtureBuilder asSensor(boolean isSensor) {
        super.asSensor(isSensor);
        return this;
    }

    @Override
    public ShapelyFixtureBuilder asSensor() {
        super.asSensor();
        return this;
    }

    @Override
    public ShapelyFixtureBuilder notAsSensor() {
        super.notAsSensor();
        return this;
    }

    @Override
    public ShapelyFixtureBuilder withCategoryBits(short categoryBits) {
        super.withCategoryBits(categoryBits);
        return this;
    }

    @Override
    public ShapelyFixtureBuilder withMaskBits(short maskBits) {
        super.withMaskBits(maskBits);
        return this;
    }

    @Override
    public ShapelyFixtureBuilder withGroupIndex(short groupIndex) {
        super.withGroupIndex(groupIndex);
        return this;
    }

    @Override
    public ShapelyFixtureBuilder withFilter(Filter filter) {
        super.withFilter(filter);
        return this;
    }

    @Override
    public ShapelyFixtureBuilder withFilter(short categoryBits, short maskBits, short groupIndex) {
        super.withFilter(categoryBits, maskBits, groupIndex);
        return this;
    }

}
