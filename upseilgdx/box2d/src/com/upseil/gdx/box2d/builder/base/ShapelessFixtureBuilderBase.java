package com.upseil.gdx.box2d.builder.base;

import com.badlogic.gdx.math.Vector2;
import com.upseil.gdx.box2d.builder.shape.ChainedBoxShapeBuilder;
import com.upseil.gdx.box2d.builder.shape.ChainedChainShapeBuilder;
import com.upseil.gdx.box2d.builder.shape.ChainedCircleShapeBuilder;
import com.upseil.gdx.box2d.builder.shape.ChainedEdgeShapeBuilder;
import com.upseil.gdx.box2d.builder.shape.ChainedPolygonShapeBuilder;

public interface ShapelessFixtureBuilderBase<N> {

    ChainedCircleShapeBuilder<N> withCircleShape(float radius);
    default N withSimpleCircleShape(float radius) {
        return withCircleShape(radius).endShape();
    }
    default N withCircleShape(float radius, float centerX, float centerY) {
        return withCircleShape(radius).at(centerX, centerY).endShape();
    }
    default N withCircleShape(float radius, Vector2 centerPosition) {
        return withCircleShape(radius, centerPosition.x, centerPosition.y);
    }
    
    ChainedBoxShapeBuilder<N> withBoxShape(float width, float height);
    default N withSimpleBoxShape(float width, float height) {
        return withBoxShape(width, height).endShape();
    }
    default N withSimpleBoxShape(float width, float height, float radius) {
        return withBoxShape(width, height).withRadius(radius).endShape();
    }
    
    ChainedPolygonShapeBuilder<N> withPolygonShape();
    
    ChainedChainShapeBuilder<N> withChainShape();
    
    ChainedEdgeShapeBuilder<N> withEdgeShape(float x1, float y1, float x2, float y2);
    default ChainedEdgeShapeBuilder<N> withEdgeShape(Vector2 vertix1, Vector2 vertix2) {
        return withEdgeShape(vertix1.x, vertix1.y, vertix2.x, vertix2.y);
    }
    default N withSimpleEdgeShape(float x1, float y1, float x2, float y2) {
        return withEdgeShape(x1, y1, x2, y2).endShape();
    }
    default N withSimpleEdgeShape(Vector2 vertix1, Vector2 vertix2) {
        return withEdgeShape(vertix1, vertix2).endShape();
    }
    
}