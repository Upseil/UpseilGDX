package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public abstract class ChainedBoxShapeBuilder<P> extends SimpleBoxShapeBuilder implements ChainedShapeBuilder<PolygonShape, P> {
    
    private final P parent;

    public ChainedBoxShapeBuilder() {
        this.parent = createParent();
    }

    protected abstract P createParent();
    
    @Override
    public P endShape() {
        return parent;
    }
    
//- Overriding members for concrete return type -----------------------------------------

    @Override
    public ChainedBoxShapeBuilder<P> withWidth(float width) {
        super.withWidth(width);
        return this;
    }

    @Override
    public ChainedBoxShapeBuilder<P> withHeight(float height) {
        super.withHeight(height);
        return this;
    }

    @Override
    public ChainedBoxShapeBuilder<P> withSize(float width, float height) {
        super.withSize(width, height);
        return this;
    }

    @Override
    public ChainedBoxShapeBuilder<P> withSize(Vector2 size) {
        super.withSize(size);
        return this;
    }

    @Override
    public ChainedBoxShapeBuilder<P> at(float centerX, float centerY) {
        super.at(centerX, centerY);
        return this;
    }

    @Override
    public ChainedBoxShapeBuilder<P> at(Vector2 centerPosition) {
        super.at(centerPosition);
        return this;
    }

    @Override
    public ChainedBoxShapeBuilder<P> rotatedByRadians(float angle) {
        super.rotatedByRadians(angle);
        return this;
    }

    @Override
    public ChainedBoxShapeBuilder<P> rotatedByDegrees(float angle) {
        super.rotatedByDegrees(angle);
        return this;
    }

    @Override
    public ChainedBoxShapeBuilder<P> withRadius(float radius) {
        super.withRadius(radius);
        return this;
    }
    
}
