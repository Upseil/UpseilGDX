package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public interface BoxShapeBuilder extends ShapeBuilderBase<BoxShapeBuilder> {
    
    BoxShapeBuilder at(float centerX, float centerY);
    default BoxShapeBuilder at(Vector2 centerPosition) {
        return at(centerPosition.x, centerPosition.y);
    }
    
    BoxShapeBuilder rotatedByRadians(float angle);
    default BoxShapeBuilder rotatedByDegrees(float angle) {
        return rotatedByRadians(angle * MathUtils.degreesToRadians);
    }
    
}