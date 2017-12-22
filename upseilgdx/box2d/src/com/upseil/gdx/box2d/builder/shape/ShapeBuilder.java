package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Disposable;

public interface ShapeBuilder<T extends Shape> extends ShapeBuilderBase<T>, Disposable {
    
    static CircleShapeBuilder createCircle() {
        return new SimpleCircleShapeBuilder();
    }
    
    static BoxShapeBuilder createBox() {
        return new SimpleBoxShapeBuilder();
    }
    
    static PolygonShapeBuilder createPolygon() {
        return new SimplePolygonShapeBuilder();
    }
    
}
