package com.upseil.gdx.box2d.builder.base;

import com.badlogic.gdx.math.Vector2;
import com.upseil.gdx.box2d.builder.shape.ChainedBoxShapeBuilder;
import com.upseil.gdx.box2d.builder.shape.ChainedCircleShapeBuilder;
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
    
}