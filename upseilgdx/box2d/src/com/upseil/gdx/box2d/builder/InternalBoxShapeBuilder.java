package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

class InternalBoxShapeBuilder extends AbstractShapeBuilderBase<BoxShapeBuilder> implements BoxShapeBuilder {
    
    private static final Vector2 tmpVector = new Vector2();
    
    public InternalBoxShapeBuilder(FixtureBuilder parent, float width, float height) {
        super(parent);
        super.builder = this;
        bounds.setSize(width, height);
        bounds.setCenter(0, 0);
    }

    @Override
    public BoxShapeBuilder at(float centerX, float centerY) {
        bounds.setCenter(centerX, centerY);
        return this;
    }

    @Override
    public BoxShapeBuilder rotatedByRadians(float angle) {
        super.angle = angle;
        return this;
    }

    @Override
    protected Shape build() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(bounds.width / 2, bounds.height / 2, bounds.getCenter(tmpVector), angle);
        shape.setRadius(radius);
        return shape;
    }
    
}