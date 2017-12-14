package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;

abstract class AbstractFixtureBuilderBase<BuilderType> implements FixtureBuilderBase<BuilderType> {
    
    protected final FixtureDef fixtureDefinition;
    private BuilderType builder;
    
    protected AbstractFixtureBuilderBase(FixtureDef fixtureDefinition) {
        this.fixtureDefinition = fixtureDefinition;
    }
    
    protected void setBuilder(BuilderType builder) {
        this.builder = builder;
    }
    
    @Override
    public BuilderType friction(float friction) {
        fixtureDefinition.friction = friction;
        return builder;
    }
    
    @Override
    public BuilderType restitution(float restitution) {
        fixtureDefinition.restitution = restitution;
        return builder;
    }
    
    @Override
    public BuilderType density(float density) {
        fixtureDefinition.density = density;
        return builder;
    }
    
    @Override
    public BuilderType isSensor(boolean isSensor) {
        fixtureDefinition.isSensor = isSensor;
        return builder;
    }

    @Override
    public BuilderType categoryBits(short categoryBits) {
        fixtureDefinition.filter.categoryBits = categoryBits;
        return builder;
    }

    @Override
    public BuilderType maskBits(short maskBits) {
        fixtureDefinition.filter.maskBits = maskBits;
        return builder;
    }

    @Override
    public BuilderType groupIndex(short groupIndex) {
        fixtureDefinition.filter.groupIndex = groupIndex;
        return builder;
    }
    
    @Override
    public BuilderType filter(Filter filter) {
        return filter(filter.categoryBits, filter.maskBits, filter.groupIndex);
    }

    @Override
    public BuilderType filter(short categoryBits, short maskBits, short groupIndex) {
        fixtureDefinition.filter.categoryBits = categoryBits;
        fixtureDefinition.filter.maskBits = maskBits;
        fixtureDefinition.filter.groupIndex = groupIndex;
        return builder;
    }
    
}
