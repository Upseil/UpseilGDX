package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Disposable;

public interface ShapeBuilderBase<BuilderType> extends Disposable {
    
    BuilderType withRadius(float radius);

    FixtureBuilder endShape();

    Shape getShape();
    Rectangle getBounds();
    float getAngle();
    
}