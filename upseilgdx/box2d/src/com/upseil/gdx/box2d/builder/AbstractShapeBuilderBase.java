package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Shape;

abstract class AbstractShapeBuilderBase<BuilderType> implements ShapeBuilderBase<BuilderType> {
    
    private final FixtureBuilder parent;
    BuilderType builder;
    
    protected Shape shape;
    protected final Rectangle bounds;
    protected float angle;
    protected float radius;

    public AbstractShapeBuilderBase(FixtureBuilder parent) {
        this.parent = parent;
        bounds = new Rectangle();
    }
    
    @Override
    public BuilderType withRadius(float radius) {
        this.radius = radius;
        return builder;
    }

    @Override
    public FixtureBuilder endShape() {
        return parent;
    }

    @Override
    public Shape getShape() {
        if (shape == null) {
            shape = build();
        }
        return shape;
    }

    protected abstract Shape build();

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public float getAngle() {
        return angle;
    }
    
    @Override
    public void dispose() {
        if (shape != null) {
            shape.dispose();
        }
    }
    
}
