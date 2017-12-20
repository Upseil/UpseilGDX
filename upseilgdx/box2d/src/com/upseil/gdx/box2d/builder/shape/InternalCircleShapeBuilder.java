package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.upseil.gdx.box2d.builder.AbstractInternalShapeBuilder;

public class InternalCircleShapeBuilder<P> extends AbstractInternalShapeBuilder<CircleShape, P> implements ChainedCircleShapeBuilder<P> {
    
    private float radius;

    public InternalCircleShapeBuilder() {
        this(null);
    }

    public InternalCircleShapeBuilder(P parent) {
        super(parent);
    }
    
    @Override
    public ChainedCircleShapeBuilder<P> withRadius(float radius) {
        if (!MathUtils.isEqual(this.radius, radius)) {
            this.radius = radius;
            bounds.getCenter(tmpVector);
            bounds.setSize(radius * 2);
            bounds.setCenter(tmpVector);
            changed = true;
        }
        return this;
    }

    @Override
    public ChainedCircleShapeBuilder<P> at(float centerX, float centerY) {
        if (!bounds.getCenter(tmpVector).epsilonEquals(centerX, centerY, MathUtils.FLOAT_ROUNDING_ERROR)) {
            bounds.setCenter(centerX, centerY);
            changed = true;
        }
        return this;
    }

    @Override
    protected CircleShape createShape() {
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        shape.setPosition(bounds.getCenter(tmpVector));
        return shape;
    }
    
}
