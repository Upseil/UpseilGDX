package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.EdgeShape;

public class SimpleEdgeShapeBuilder extends AbstractVertixBasedShapeBuilder<EdgeShape> implements EdgeShapeBuilder {

    protected static final int GhostVertix1 = 0;
    protected static final int Vertix1 = 1;
    protected static final int Vertix2 = 2;
    protected static final int GhostVertix2 = 3;

    protected float radius;

    protected boolean hasGhostVertix1;
    protected boolean hasGhostVertix2;
    
    public SimpleEdgeShapeBuilder() {
        super(-1, 4);
        lastVertices.setSize(8);
    }
    
    @Override
    public EdgeShapeBuilder withRadius(float radius) {
        if (!MathUtils.isEqual(this.radius, radius)) {
            this.radius = radius;
            changed = true;
        }
        return this;
    }
    
    @Override
    public EdgeShapeBuilder withVertix1(float x, float y) {
        internalSet(Vertix1, x, y);
        return this;
    }
    
    @Override
    public EdgeShapeBuilder withGhostVertix1(float x, float y) {
        internalSet(GhostVertix1, x, y);
        hasGhostVertix1 = true;
        return this;
    }
    
    @Override
    public EdgeShapeBuilder withVertix2(float x, float y) {
        internalSet(Vertix2, x, y);
        return this;
    }
    
    @Override
    public EdgeShapeBuilder withGhostVertix2(float x, float y) {
        internalSet(GhostVertix2, x, y);
        hasGhostVertix2 = true;
        return this;
    }
    
    @Override
    protected EdgeShape createShape() {
        float[] vertices = vertices().items;
        
        EdgeShape shape = new EdgeShape();
        shape.set(vertices[Vertix1 * 2], vertices[Vertix1 * 2 + 1],
                  vertices[Vertix2 * 2], vertices[Vertix2 * 2 + 1]);
        
        if (hasGhostVertix1) {
            shape.setVertex0(vertices[GhostVertix1 * 2], vertices[GhostVertix1 * 2 + 1]);
            shape.setHasVertex0(true);
        }
        if (hasGhostVertix2) {
            shape.setVertex3(vertices[GhostVertix2 * 2], vertices[GhostVertix2 * 2 + 1]);
            shape.setHasVertex3(true);
        }
        
        return shape;
    }
    
}
