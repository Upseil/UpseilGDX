package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.physics.box2d.FixtureDef;

abstract class AbstractFixtureBuilderBase<BuilderType> implements FixtureBuilderBase<BuilderType> {
    
    BuilderType builder;
    
    protected final FixtureDef fixtureDefinition;
    
    protected AbstractFixtureBuilderBase(FixtureDef fixtureDefinition) {
        this.fixtureDefinition = fixtureDefinition;
    }
    
    @Override
    public BuilderType withFriction(float friction) {
        fixtureDefinition.friction = friction;
        return builder;
    }
    
    @Override
    public BuilderType withRestitution(float restitution) {
        fixtureDefinition.restitution = restitution;
        return builder;
    }
    
    @Override
    public BuilderType withDensity(float density) {
        fixtureDefinition.density = density;
        return builder;
    }
    
    @Override
    public BuilderType asSensor(boolean isSensor) {
        fixtureDefinition.isSensor = isSensor;
        return builder;
    }

    @Override
    public BuilderType withCategoryBits(short categoryBits) {
        fixtureDefinition.filter.categoryBits = categoryBits;
        return builder;
    }

    @Override
    public BuilderType withMaskBits(short maskBits) {
        fixtureDefinition.filter.maskBits = maskBits;
        return builder;
    }
    
    @Override
    public BuilderType withGroupIndex(short groupIndex) {
        fixtureDefinition.filter.groupIndex = groupIndex;
        return builder;
    }
    
}
