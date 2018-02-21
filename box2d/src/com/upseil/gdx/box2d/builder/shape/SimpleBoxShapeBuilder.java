package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class SimpleBoxShapeBuilder extends AbstractShapeBuilder<PolygonShape> implements BoxShapeBuilder {
    
    private float radius;

    @Override
    public BoxShapeBuilder withWidth(float width) {
        if (!MathUtils.isEqual(bounds.width, width)) {
            bounds.getCenter(tmpVector);
            bounds.setWidth(width);
            bounds.setX(tmpVector.x - width / 2);
            changed = true;
        }
        return this;
    }

    @Override
    public BoxShapeBuilder withHeight(float height) {
        if (!MathUtils.isEqual(bounds.height, height)) {
            bounds.getCenter(tmpVector);
            bounds.setHeight(height);
            bounds.setY(tmpVector.y - height / 2);
            changed = true;
        }
        return this;
    }

    @Override
    public BoxShapeBuilder withRadius(float radius) {
        if (!MathUtils.isEqual(this.radius, radius)) {
            this.radius = radius;
            changed = true;
        }
        return this;
    }

    @Override
    public BoxShapeBuilder at(float centerX, float centerY) {
        if (!bounds.getCenter(tmpVector).epsilonEquals(centerX, centerY)) {
            bounds.setCenter(centerX, centerY);
            changed = true;
        }
        return this;
    }

    @Override
    public BoxShapeBuilder rotatedByRadians(float angle) {
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