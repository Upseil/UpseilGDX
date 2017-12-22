package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.CircleShape;

public class SimpleCircleShapeBuilder extends AbstractShapeBuilder<CircleShape> implements CircleShapeBuilder {
    
    private float radius;
    
    @Override
    public CircleShapeBuilder withRadius(float radius) {
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
    public CircleShapeBuilder at(float centerX, float centerY) {
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
