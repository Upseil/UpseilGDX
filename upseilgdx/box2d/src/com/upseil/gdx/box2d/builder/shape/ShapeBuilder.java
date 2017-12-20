package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Disposable;
import com.upseil.gdx.util.builder.Builder;

public interface ShapeBuilder<T extends Shape> extends Builder<T>, Disposable {

    Rectangle getBounds();
    float getAngle();
    
    static CircleShapeBuilder circle() {
        return new InternalCircleShapeBuilder<Void>();
    }
    
    static BoxShapeBuilder box() {
        return new InternalBoxShapeBuilder<Void>();
    }
    
    static PolygonShapeBuilder polygon() {
        return new InternalPolygonShapeBuilder<Void>();
    }
    
}
