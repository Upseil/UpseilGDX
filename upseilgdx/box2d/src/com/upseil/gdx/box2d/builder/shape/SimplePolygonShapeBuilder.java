package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.FloatArray;

public class SimplePolygonShapeBuilder extends AbstractShapeBuilder<PolygonShape> implements PolygonShapeBuilder {
    
    private float radius;
    private final FloatArray vertices;
    
    public SimplePolygonShapeBuilder() {
        vertices = new FloatArray(16);
    }
    
    @Override
    public PolygonShapeBuilder withRadius(float radius) {
        if (!MathUtils.isEqual(this.radius, radius)) {
            this.radius = radius;
            changed = true;
        }
        return this;
    }

    @Override
    public PolygonShapeBuilder addVertix(float x, float y) {
        vertices.add(x);
        vertices.add(y);
        bounds.merge(x, y);
        return this;
    }
    
    @Override
    protected PolygonShape createShape() {
        if (vertices.size < 6) {
            throw new IllegalStateException("Only " + (vertices.size / 2) + " have been defined, but at least 3 are necessary");
        }
        if (vertices.size > 16) {
            throw new IllegalStateException((vertices.size / 2) + " have been defined, but Box2D only supports 8 per polygon");
        }
        PolygonShape shape = new PolygonShape();
        shape.set(vertices.items, 0, vertices.size);
        shape.setRadius(radius);
        return shape;
    }
    
}
