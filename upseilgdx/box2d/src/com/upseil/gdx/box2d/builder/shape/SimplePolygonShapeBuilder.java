package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.FloatArray;

public class SimplePolygonShapeBuilder extends AbstractVertixBasedShapeBuilder<PolygonShape> implements PolygonShapeBuilder {
    
    private float radius;
    private boolean normalize;
    
    public SimplePolygonShapeBuilder() {
        super(-1, 8);
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
        internalAddLast(x, y);
        return this;
    }
    
    @Override
    public PolygonShapeBuilder normalizeOriginToCenter() {
        normalize = true;
        return this;
    }
    
    @Override
    protected PolygonShape createShape() {
        if (normalize) super.normalizeVertices();
        FloatArray vertices = vertices();
        if (vertices.size < 6) {
            throw new IllegalStateException("Only " + (vertices.size / 2) + " vertices have been defined, but at least 3 are necessary");
        }
        if (vertices.size > 16) {
            throw new IllegalStateException((vertices.size / 2) + " vertices have been defined, but Box2D only supports 8 per polygon");
        }
        PolygonShape shape = new PolygonShape();
        shape.set(vertices.items, 0, vertices.size);
        shape.setRadius(radius);
        return shape;
    }
    
}
