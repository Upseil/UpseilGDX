package com.upseil.gdx.box2d.builder.base;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.utils.Disposable;
import com.upseil.gdx.util.builder.Builder;

public interface FixtureBuilderBase<T> extends Builder<T>, Disposable {
    
    FixtureBuilderBase<T> withFriction(float friction);
    FixtureBuilderBase<T> withRestitution(float restitution);
    FixtureBuilderBase<T> withDensity(float density);
    
    FixtureBuilderBase<T> asSensor(boolean isSensor);
    default FixtureBuilderBase<T> asSensor() {
        return asSensor(true);
    }
    default FixtureBuilderBase<T> notAsSensor() {
        return asSensor(false);
    }
    
    FixtureBuilderBase<T> withCategoryBits(short categoryBits);
    FixtureBuilderBase<T> withMaskBits(short maskBits);
    FixtureBuilderBase<T> withGroupIndex(short groupIndex);
    
    default FixtureBuilderBase<T> withFilter(Filter filter) {
        withCategoryBits(filter.categoryBits);
        withMaskBits(filter.maskBits);
        return withGroupIndex(filter.groupIndex);
    }
    
    default FixtureBuilderBase<T> withFilter(short categoryBits, short maskBits, short groupIndex) {
        withCategoryBits(categoryBits);
        withMaskBits(maskBits);
        return withGroupIndex(groupIndex);
    }
    
//- Utility methods ---------------------------------------------------------------------
    
    Rectangle getBounds();
    float getAngle();

    boolean hasCategoryBits();
    boolean hasMaskBits();
    boolean hasGroupIndex();
    
}