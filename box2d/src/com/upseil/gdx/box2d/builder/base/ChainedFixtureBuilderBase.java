package com.upseil.gdx.box2d.builder.base;

import com.badlogic.gdx.physics.box2d.Filter;

public interface ChainedFixtureBuilderBase<T, P> extends FixtureBuilderBase<T> {
    
    P endFixture();
    
//- Overriding members for concrete return type -----------------------------------------
    
    ChainedFixtureBuilderBase<T, P> withFriction(float friction);
    ChainedFixtureBuilderBase<T, P> withRestitution(float restitution);
    ChainedFixtureBuilderBase<T, P> withDensity(float density);
    
    ChainedFixtureBuilderBase<T, P> asSensor(boolean isSensor);
    default ChainedFixtureBuilderBase<T, P> asSensor() {
        FixtureBuilderBase.super.asSensor();
        return this;
    }
    default ChainedFixtureBuilderBase<T, P> notAsSensor() {
        FixtureBuilderBase.super.notAsSensor();
        return this;
    }
    
    ChainedFixtureBuilderBase<T, P> withCategoryBits(short categoryBits);
    ChainedFixtureBuilderBase<T, P> withMaskBits(short maskBits);
    ChainedFixtureBuilderBase<T, P> withGroupIndex(short groupIndex);
    
    default ChainedFixtureBuilderBase<T, P> withFilter(Filter filter) {
        FixtureBuilderBase.super.withFilter(filter);
        return this;
    }
    
    default ChainedFixtureBuilderBase<T, P> withFilter(short categoryBits, short maskBits, short groupIndex) {
        FixtureBuilderBase.super.withFilter(categoryBits, maskBits, groupIndex);
        return this;
    }
    
}
