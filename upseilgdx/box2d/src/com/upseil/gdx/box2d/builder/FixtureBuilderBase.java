package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.physics.box2d.Filter;

interface FixtureBuilderBase<BuilderType> {
    
    BuilderType friction(float friction);
    BuilderType restitution(float restitution);
    BuilderType density(float density);
    
    BuilderType isSensor(boolean isSensor);
    
    BuilderType categoryBits(short categoryBits);
    BuilderType maskBits(short maskBits);
    BuilderType groupIndex(short groupIndex);
    
    BuilderType filter(Filter filter);
    BuilderType filter(short categoryBits, short maskBits, short groupIndex);
    
}