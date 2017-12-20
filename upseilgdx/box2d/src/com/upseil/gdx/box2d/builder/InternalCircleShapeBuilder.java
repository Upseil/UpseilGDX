package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;

public class InternalCircleShapeBuilder extends AbstractShapeBuilderBase<CircleShapeBuilder> implements CircleShapeBuilder {
    
    private static final Vector2 tmpVector = new Vector2();
    
    public InternalCircleShapeBuilder(FixtureBuilder parent, float radius) {
        super(parent);
        super.builder = this;
        
        this.radius = radius;
        bounds.setSize(radius * 2);
        bounds.setCenter(0, 0);
    }

    @Override
    public CircleShapeBuilder at(float centerX, float centerY) {
        bounds.setCenter(centerX, centerY);
        return this;
    }

    @Override
    protected Shape build() {
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        shape.setPosition(bounds.getCenter(tmpVector));
        return shape;
    }
    
}
