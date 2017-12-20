package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.physics.box2d.Filter;

interface FixtureBuilderBase<BuilderType> {
    
    BuilderType withFriction(float friction);
    BuilderType withRestitution(float restitution);
    BuilderType withDensity(float density);
    
    BuilderType asSensor(boolean isSensor);
    default BuilderType asSensor() {
        return asSensor(true);
    }
    default BuilderType notAsSensor() {
        return asSensor(false);
    }
    
    BuilderType withCategoryBits(short categoryBits);
    BuilderType withMaskBits(short maskBits);
    BuilderType withGroupIndex(short groupIndex);
    
    default BuilderType withFilter(Filter filter) {
        withCategoryBits(filter.categoryBits);
        withMaskBits(filter.maskBits);
        return withGroupIndex(filter.groupIndex);
    }
    
    default BuilderType withFilter(short categoryBits, short maskBits, short groupIndex) {
        withCategoryBits(categoryBits);
        withMaskBits(maskBits);
        return withGroupIndex(groupIndex);
    }
    
}