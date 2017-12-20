package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import com.upseil.gdx.box2d.builder.shape.ChainedShapeBuilder;

public abstract class AbstractInternalShapeBuilder<T extends Shape, P> implements ChainedShapeBuilder<T, P> {
    
    protected static final Vector2 tmpVector = new Vector2();
    
    private final P parent;
    protected T shape;
    protected boolean changed;
    
    protected final Rectangle bounds;
    protected float angle;

    public AbstractInternalShapeBuilder() {
        this(null);
    }

    public AbstractInternalShapeBuilder(P parent) {
        this.parent = parent;
        bounds = new Rectangle();
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public float getAngle() {
        return angle;
    }
    
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
    public P endShape() {
        return parent;
    }

    @Override
    public void dispose() {
        if (shape != null) {
            shape.dispose();
        }
    }
    
}
