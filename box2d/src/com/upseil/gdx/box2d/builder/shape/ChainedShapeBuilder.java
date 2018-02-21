package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.physics.box2d.Shape;

public interface ChainedShapeBuilder<T extends Shape, P> extends ShapeBuilder<T> {
    
    P endShape();
    
}
