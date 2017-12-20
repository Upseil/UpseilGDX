package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public interface FixtureBuilder extends FixtureBuilderBase<FixtureBuilder> {
    
    FixtureBuilder withImage(Drawable image);
    FixtureBuilder withImage(String name);
    
    FixtureBuilder withColor(Color color);
    FixtureBuilder withColor(String name); 
    
    BodiedActorBuilder endFixture();
    
}