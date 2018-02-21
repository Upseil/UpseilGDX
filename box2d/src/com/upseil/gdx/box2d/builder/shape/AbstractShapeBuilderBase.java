package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractShapeBuilderBase<T> implements ShapeBuilderBase<T> {
    
    protected static final Vector2 tmpVector = new Vector2();
    
    protected final Rectangle bounds;
    protected float angle;
    protected boolean changed;
    
    protected AbstractShapeBuilderBase() {
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
    
}
