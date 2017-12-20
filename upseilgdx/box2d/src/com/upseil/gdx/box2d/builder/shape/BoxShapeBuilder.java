package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public interface BoxShapeBuilder extends ShapeBuilder<PolygonShape> {
    
    BoxShapeBuilder withWidth(float width);
    BoxShapeBuilder withHeight(float height);
    default BoxShapeBuilder withSize(float width, float height) {
        withWidth(width);
        withHeight(height);
        return this;
    }
    default BoxShapeBuilder withSize(Vector2 size) {
        return withSize(size.x, size.y);
    }
    
    BoxShapeBuilder withRadius(float radius);
    
    BoxShapeBuilder at(float centerX, float centerY);
    default BoxShapeBuilder at(Vector2 centerPosition) {
        return at(centerPosition.x, centerPosition.y);
    }
    
    BoxShapeBuilder rotatedByRadians(float angle);
    default BoxShapeBuilder rotatedByDegrees(float angle) {
        return rotatedByRadians(angle * MathUtils.degreesToRadians);
    }
    
}