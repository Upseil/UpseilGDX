package com.upseil.gdx.box2d.builder.base;

import com.upseil.gdx.box2d.builder.shape.ChainedBoxShapeBuilder;
import com.upseil.gdx.box2d.builder.shape.ChainedCircleShapeBuilder;
import com.upseil.gdx.box2d.builder.shape.ChainedPolygonShapeBuilder;
import com.upseil.gdx.box2d.builder.shape.ShapeBuilder;

public abstract class AbstractShapelessFixtureBuilderBase<N> implements ShapelessFixtureBuilderBase<N> {

    @Override
    public ChainedCircleShapeBuilder<N> withCircleShape(float radius) {
        ChainedCircleShapeBuilder<N> circleBuilder = new ChainedCircleShapeBuilder<N>() {
            @Override
            protected N createParent() {
                return createShapelyFixtureBuilder(this);
            }
        };
        circleBuilder.withRadius(radius);
        return circleBuilder;
    }

    @Override
    public ChainedBoxShapeBuilder<N> withBoxShape(float width, float height) {
        ChainedBoxShapeBuilder<N> boxShapeBuilder = new ChainedBoxShapeBuilder<N>() {
            @Override
            protected N createParent() {
                return createShapelyFixtureBuilder(this);
            }
        };
        boxShapeBuilder.withSize(width, height);
        return boxShapeBuilder;
    }

    @Override
    public ChainedPolygonShapeBuilder<N> withPolygonShape() {
        ChainedPolygonShapeBuilder<N> polygonShapeBuilder = new ChainedPolygonShapeBuilder<N>() {
            @Override
            protected N createParent() {
                return createShapelyFixtureBuilder(this);
            }
        };
        return polygonShapeBuilder;
    }
    
    protected abstract N createShapelyFixtureBuilder(ShapeBuilder<?> shape);
    
}
