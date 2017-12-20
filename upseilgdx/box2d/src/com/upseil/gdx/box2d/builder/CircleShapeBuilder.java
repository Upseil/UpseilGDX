package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.math.Vector2;

public interface CircleShapeBuilder extends ShapeBuilderBase<CircleShapeBuilder> {
    
    CircleShapeBuilder at(float centerX, float centerY);
    default CircleShapeBuilder at(Vector2 centerPosition) {
        return at(centerPosition.x, centerPosition.y);
    }
    
}
