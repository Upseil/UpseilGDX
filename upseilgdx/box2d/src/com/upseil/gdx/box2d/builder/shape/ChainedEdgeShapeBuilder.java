package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.EdgeShape;

public abstract class ChainedEdgeShapeBuilder<P> extends SimpleEdgeShapeBuilder implements ChainedShapeBuilder<EdgeShape, P> {
    
    private final P parent;

    public ChainedEdgeShapeBuilder() {
        this.parent = createParent();
    }

    protected abstract P createParent();
    
    @Override
    public P endShape() {
        return parent;
    }
    
//- Overriding members for concrete return type -----------------------------------------

    @Override
    public ChainedEdgeShapeBuilder<P> withVertix1(float x, float y, float ghostX, float ghostY) {
        super.withVertix1(x, y, ghostX, ghostY);
        return this;
    }

    @Override
    public ChainedEdgeShapeBuilder<P> withVertix1(Vector2 vertix, Vector2 ghostVertix) {
        super.withVertix1(vertix, ghostVertix);
        return this;
    }

    @Override
    public ChainedEdgeShapeBuilder<P> withVertix2(float x, float y, float ghostX, float ghostY) {
        super.withVertix2(x, y, ghostX, ghostY);
        return this;
    }

    @Override
    public ChainedEdgeShapeBuilder<P> withVertix2(Vector2 vertix, Vector2 ghostVertix) {
        super.withVertix2(vertix, ghostVertix);
        return this;
    }

    @Override
    public ChainedEdgeShapeBuilder<P> set(float x1, float y1, float x2, float y2) {
        super.set(x1, y1, x2, y2);
        return this;
    }

    @Override
    public ChainedEdgeShapeBuilder<P> set(Vector2 vertix1, Vector2 vertix2) {
        super.set(vertix1, vertix2);
        return this;
    }

    @Override
    public ChainedEdgeShapeBuilder<P> set(float x1, float y1, float ghostX1, float ghostY1, float x2, float y2, float ghostX2, float ghostY2) {
        super.set(x1, y1, ghostX1, ghostY1, x2, y2, ghostX2, ghostY2);
        return this;
    }

    @Override
    public ChainedEdgeShapeBuilder<P> set(Vector2 vertix1, Vector2 ghostVertix1, Vector2 vertix2, Vector2 ghostVertix2) {
        super.set(vertix1, ghostVertix1, vertix2, ghostVertix2);
        return this;
    }

    @Override
    public ChainedEdgeShapeBuilder<P> withRadius(float radius) {
        super.withRadius(radius);
        return this;
    }

    @Override
    public ChainedEdgeShapeBuilder<P> withVertix1(float x, float y) {
        super.withVertix1(x, y);
        return this;
    }

    @Override
    public ChainedEdgeShapeBuilder<P> withGhostVertix1(float x, float y) {
        super.withGhostVertix1(x, y);
        return this;
    }

    @Override
    public ChainedEdgeShapeBuilder<P> withVertix2(float x, float y) {
        super.withVertix2(x, y);
        return this;
    }

    @Override
    public ChainedEdgeShapeBuilder<P> withGhostVertix2(float x, float y) {
        super.withGhostVertix2(x, y);
        return this;
    }
    
}
