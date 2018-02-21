package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;

public interface CircleShapeBuilder extends ShapeBuilder<CircleShape> {
    
    CircleShapeBuilder withRadius(float radius);
    
    CircleShapeBuilder at(float centerX, float centerY);
    default CircleShapeBuilder at(Vector2 centerPosition) {
        return at(centerPosition.x, centerPosition.y);
    }
    
}
