package com.upseil.gdx.box2d.builder.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public interface FixturedActorBuilderBase<T> extends FixtureBuilderBase<T> {
    
    FixturedActorBuilderBase<T> withImage(Drawable image);
    FixturedActorBuilderBase<T> withImage(String name);
    
    FixturedActorBuilderBase<T> withTexture(TextureRegion texture);
    FixturedActorBuilderBase<T> withTexture(String name);
    
    FixturedActorBuilderBase<T> withColor(Color color);
    FixturedActorBuilderBase<T> withColor(String name); 
    
//- Overriding members for concrete return type -----------------------------------------
    
    FixturedActorBuilderBase<T> withFriction(float friction);
    FixturedActorBuilderBase<T> withRestitution(float restitution);
    FixturedActorBuilderBase<T> withDensity(float density);
    
    FixturedActorBuilderBase<T> asSensor(boolean isSensor);
    default FixturedActorBuilderBase<T> asSensor() {
        FixtureBuilderBase.super.asSensor();
        return this;
    }
    default FixturedActorBuilderBase<T> notAsSensor() {
        FixtureBuilderBase.super.notAsSensor();
        return this;
    }
    
    FixturedActorBuilderBase<T> withCategoryBits(short categoryBits);
    FixturedActorBuilderBase<T> withMaskBits(short maskBits);
    FixturedActorBuilderBase<T> withGroupIndex(short groupIndex);
    
    default FixturedActorBuilderBase<T> withFilter(Filter filter) {
        FixtureBuilderBase.super.withFilter(filter);
        return this;
    }
    
    default FixturedActorBuilderBase<T> withFilter(short categoryBits, short maskBits, short groupIndex) {
        FixtureBuilderBase.super.withFilter(categoryBits, maskBits, groupIndex);
        return this;
    }
    
}