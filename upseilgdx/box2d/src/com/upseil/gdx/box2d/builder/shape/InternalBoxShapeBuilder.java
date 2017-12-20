package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.upseil.gdx.box2d.builder.AbstractInternalShapeBuilder;

public class InternalBoxShapeBuilder<P> extends AbstractInternalShapeBuilder<PolygonShape, P> implements ChainedBoxShapeBuilder<P> {
    
    private float radius;
    
    public InternalBoxShapeBuilder() {
        this(null);
    }
    
    public InternalBoxShapeBuilder(P parent) {
        super(parent);
    }

    @Override
    public ChainedBoxShapeBuilder<P> withWidth(float width) {
        if (!MathUtils.isEqual(bounds.width, width)) {
            bounds.getCenter(tmpVector);
            bounds.setWidth(width);
            bounds.setX(tmpVector.x - width / 2);
            changed = true;
        }
        return this;
    }

    @Override
    public ChainedBoxShapeBuilder<P> withHeight(float height) {
        if (!MathUtils.isEqual(bounds.height, height)) {
            bounds.getCenter(tmpVector);
            bounds.setHeight(height);
            bounds.setY(tmpVector.y - height / 2);
            changed = true;
        }
        return this;
    }

    @Override
    public ChainedBoxShapeBuilder<P> withRadius(float radius) {
        if (!MathUtils.isEqual(this.radius, radius)) {
            this.radius = radius;
            changed = true;
        }
        return this;
    }

    @Override
    public ChainedBoxShapeBuilder<P> at(float centerX, float centerY) {
        if (!bounds.getCenter(tmpVector).epsilonEquals(centerX, centerY, MathUtils.FLOAT_ROUNDING_ERROR)) {
            bounds.setCenter(centerX, centerY);
            changed = true;
        }
        return this;
    }

    @Override
    public ChainedBoxShapeBuilder<P> rotatedByRadians(float angle) {
        if (!MathUtils.isEqual(this.angle, angle)) {
            this.angle = angle;
            changed = true;
        }
        return this;
    }

    @Override
    protected PolygonShape createShape() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(bounds.width / 2, bounds.height / 2, bounds.getCenter(tmpVector), angle);
        shape.setRadius(radius);
        return shape;
    }
    
}