package com.upseil.gdx.box2d.builder.base;

import com.upseil.gdx.box2d.builder.shape.ChainedBoxShapeBuilder;
import com.upseil.gdx.box2d.builder.shape.ChainedChainShapeBuilder;
import com.upseil.gdx.box2d.builder.shape.ChainedCircleShapeBuilder;
import com.upseil.gdx.box2d.builder.shape.ChainedEdgeShapeBuilder;
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
    
    @Override
    public ChainedChainShapeBuilder<N> withChainShape() {
        ChainedChainShapeBuilder<N> chainShapeBuilder = new ChainedChainShapeBuilder<N>() {
            @Override
            protected N createParent() {
                return createShapelyFixtureBuilder(this);
            }
        };
        return chainShapeBuilder;
    }
    
    @Override
    public ChainedEdgeShapeBuilder<N> withEdgeShape(float x1, float y1, float x2, float y2) {
        ChainedEdgeShapeBuilder<N> edgeShapeBuilder = new ChainedEdgeShapeBuilder<N>() {
            @Override
            protected N createParent() {
                return createShapelyFixtureBuilder(this);
            }
        };
        edgeShapeBuilder.set(x1, y1, x2, y2);
        return edgeShapeBuilder;
    }
    
    protected abstract N createShapelyFixtureBuilder(ShapeBuilder<?> shape);
    
}
