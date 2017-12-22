package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public abstract class ChainedPolygonShapeBuilder<P> extends SimplePolygonShapeBuilder implements ChainedShapeBuilder<PolygonShape, P> {
    
    private final P parent;

    public ChainedPolygonShapeBuilder() {
        this.parent = createParent();
    }

    protected abstract P createParent();
    
    @Override
    public P endShape() {
        return parent;
    }
    
//- Overriding members for concrete return type -----------------------------------------

    @Override
    public ChainedPolygonShapeBuilder<P> addVertix(float x, float y) {
        super.addVertix(x, y);
        return this;
    }

    @Override
    public ChainedPolygonShapeBuilder<P> addVertix(Vector2 vertix) {
        super.addVertix(vertix);
        return this;
    }

    @Override
    public ChainedPolygonShapeBuilder<P> vertices(Vector2[] vertices) {
        super.vertices(vertices);
        return this;
    }

    @Override
    public ChainedPolygonShapeBuilder<P> vertices(float[] vertices) {
        super.vertices(vertices);
        return this;
    }

    @Override
    public ChainedPolygonShapeBuilder<P> withRadius(float radius) {
        super.withRadius(radius);
        return this;
    }
    
}
