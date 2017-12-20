package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.FloatArray;

public class InternalPolygonShapeBuilder extends AbstractShapeBuilderBase<PolygonShapeBuilder> implements PolygonShapeBuilder {
    
    private final FloatArray vertices;
    
    public InternalPolygonShapeBuilder(FixtureBuilder parent) {
        super(parent);
        super.builder = this;
        vertices = new FloatArray(16);
    }

    @Override
    public PolygonShapeBuilder addVertix(float x, float y) {
        vertices.add(x);
        vertices.add(y);
        bounds.merge(x, y);
        return this;
    }
    
    @Override
    protected Shape build() {
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
