package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public abstract class ChainedPolygonShapeBuilder<P> extends SimplePolygonShapeBuilder implements ChainedShapeBuilder<PolygonShape, P> {
    
    protected final P parent;

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
    public ChainedPolygonShapeBuilder<P> at(float centerX, float centerY) {
        super.at(centerX, centerY);
        return this;
    }

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
    public ChainedPolygonShapeBuilder<P> withVertices(Vector2[] vertices) {
        super.withVertices(vertices);
        return this;
    }

    @Override
    public ChainedPolygonShapeBuilder<P> withVertices(float[] vertices) {
        super.withVertices(vertices);
        return this;
    }

    @Override
    public ChainedPolygonShapeBuilder<P> withRadius(float radius) {
        super.withRadius(radius);
        return this;
    }
    
}
