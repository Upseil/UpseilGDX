package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public interface ChainedBoxShapeBuilder<P> extends BoxShapeBuilder, ChainedShapeBuilder<PolygonShape, P> {
    
    ChainedBoxShapeBuilder<P> withWidth(float width);
    ChainedBoxShapeBuilder<P> withHeight(float height);
    default ChainedBoxShapeBuilder<P> withSize(float width, float height) {
        withWidth(width);
        withHeight(height);
        return this;
    }
    default ChainedBoxShapeBuilder<P> withSize(Vector2 size) {
        return withSize(size.x, size.y);
    }
    
    ChainedBoxShapeBuilder<P> withRadius(float radius);
    
    ChainedBoxShapeBuilder<P> at(float centerX, float centerY);
    default ChainedBoxShapeBuilder<P> at(Vector2 centerPosition) {
        return at(centerPosition.x, centerPosition.y);
    }
    
    ChainedBoxShapeBuilder<P> rotatedByRadians(float angle);
    default ChainedBoxShapeBuilder<P> rotatedByDegrees(float angle) {
        return rotatedByRadians(angle * MathUtils.degreesToRadians);
    }
    
}
