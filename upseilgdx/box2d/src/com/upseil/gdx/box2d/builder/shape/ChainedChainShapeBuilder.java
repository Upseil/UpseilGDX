package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;

public abstract class ChainedChainShapeBuilder<P> extends SimpleChainShapeBuilder implements ChainedShapeBuilder<ChainShape, P> {
    
    private final P parent;

    public ChainedChainShapeBuilder() {
        this.parent = createParent();
    }

    protected abstract P createParent();
    
    @Override
    public P endShape() {
        return parent;
    }
    
//- Overriding members for concrete return type -----------------------------------------

    @Override
    public ChainedChainShapeBuilder<P> withRadius(float radius) {
        super.withRadius(radius);
        return this;
    }

    @Override
    public ChainedChainShapeBuilder<P> asLoop(boolean isLoop) {
        super.asLoop(isLoop);
        return this;
    }

    @Override
    public ChainedChainShapeBuilder<P> asLoop() {
        super.asLoop();
        return this;
    }

    @Override
    public ChainedChainShapeBuilder<P> notAsLoop() {
        super.notAsLoop();
        return this;
    }

    @Override
    public ChainedChainShapeBuilder<P> addFirst(float x, float y) {
        super.addFirst(x, y);
        return this;
    }

    @Override
    public ChainedChainShapeBuilder<P> addFirst(Vector2 vertix) {
        super.addFirst(vertix);
        return this;
    }

    @Override
    public ChainedChainShapeBuilder<P> addLast(float x, float y) {
        super.addLast(x, y);
        return this;
    }

    @Override
    public ChainedChainShapeBuilder<P> addLast(Vector2 vertix) {
        super.addLast(vertix);
        return this;
    }

    @Override
    public ChainedChainShapeBuilder<P> withVertices(Vector2[] vertices) {
        super.withVertices(vertices);
        return this;
    }

    @Override
    public ChainedChainShapeBuilder<P> withVertices(float[] vertices) {
        super.withVertices(vertices);
        return this;
    }
    
}
