package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.math.Vector2;

public interface ShapelessFixtureBuilder {

    CircleShapeBuilder withCircleShape(float radius);
    default FixtureBuilder withCircleShape(float radius, float centerX, float centerY) {
        return withCircleShape(radius).at(centerX, centerY).endShape();
    }
    default FixtureBuilder withCircleShape(float radius, Vector2 centerPosition) {
        return withCircleShape(radius, centerPosition.x, centerPosition.y);
    }
    
    BoxShapeBuilder withBoxShape(float width, float height);
    default FixtureBuilder withSimpleBoxShape(float width, float height) {
        return withBoxShape(width, height).endShape();
    }
    default FixtureBuilder withSimpleBoxShape(float width, float height, float radius) {
        return withBoxShape(width, height).withRadius(radius).endShape();
    }
    
    PolygonShapeBuilder withPolygonShape();
    
}