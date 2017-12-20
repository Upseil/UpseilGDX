package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;

public interface ChainedCircleShapeBuilder<P> extends CircleShapeBuilder, ChainedShapeBuilder<CircleShape, P> {
    
    ChainedCircleShapeBuilder<P> withRadius(float radius);
    
    ChainedCircleShapeBuilder<P> at(float centerX, float centerY);
    default ChainedCircleShapeBuilder<P> at(Vector2 centerPosition) {
        return at(centerPosition.x, centerPosition.y);
    }
    
}
