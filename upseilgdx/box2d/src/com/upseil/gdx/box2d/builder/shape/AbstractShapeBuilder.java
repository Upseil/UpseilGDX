package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.physics.box2d.Shape;

public abstract class AbstractShapeBuilder<T extends Shape> extends AbstractShapeBuilderBase<T> implements ShapeBuilder<T> {
    
    protected T shape;
    
    @Override
    public T build() {
        if (shape == null || changed) {
            dispose();
            shape = createShape();
            changed = false;
        }
        return shape;
    }
    
    protected abstract T createShape();

    @Override
    public void dispose() {
        if (shape != null) {
            shape.dispose();
        }
    }
    
}
