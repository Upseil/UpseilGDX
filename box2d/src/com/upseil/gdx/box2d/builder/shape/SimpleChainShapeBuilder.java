package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.utils.FloatArray;

public class SimpleChainShapeBuilder extends AbstractVertixBasedShapeBuilder<ChainShape> implements ChainShapeBuilder {
    
    private float radius;
    private boolean isLoop;

    public SimpleChainShapeBuilder() {
        super(4, 8);
    }
    
    @Override
    public ChainShapeBuilder withRadius(float radius) {
        if (!MathUtils.isEqual(this.radius, radius)) {
            this.radius = radius;
            changed = true;
        }
        return this;
    }

    @Override
    public ChainShapeBuilder asLoop(boolean isLoop) {
        if (this.isLoop != isLoop) {
            this.isLoop = isLoop;
            changed = true;
        }
        return this;
    }

    @Override
    public ChainShapeBuilder addFirst(float x, float y) {
        internalAddFirst(x, y);
        return this;
    }

    @Override
    public ChainShapeBuilder addLast(float x, float y) {
        internalAddLast(x, y);
        return this;
    }

    @Override
    protected ChainShape createShape() {
        FloatArray vertices = vertices();
        ChainShape shape = new ChainShape();
        if (isLoop) {
            shape.createLoop(vertices.toArray());
        } else {
            shape.createChain(vertices.toArray());
        }
        return shape;
    }
    
}
