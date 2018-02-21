package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;

public abstract class ChainedCircleShapeBuilder<P> extends SimpleCircleShapeBuilder implements ChainedShapeBuilder<CircleShape, P> {
    
    private final P parent;

    public ChainedCircleShapeBuilder() {
        this.parent = createParent();
    }

    protected abstract P createParent();
    
    @Override
    public P endShape() {
        return parent;
    }
    
//- Overriding members for concrete return type -----------------------------------------

    @Override
    public ChainedCircleShapeBuilder<P> at(Vector2 centerPosition) {
        super.at(centerPosition);
        return this;
    }

    @Override
    public ChainedCircleShapeBuilder<P> withRadius(float radius) {
        super.withRadius(radius);
        return this;
    }

    @Override
    public ChainedCircleShapeBuilder<P> at(float centerX, float centerY) {
        super.at(centerX, centerY);
        return this;
    }
    
}
