package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.math.Vector2;

public interface PolygonShapeBuilder extends ShapeBuilderBase<PolygonShapeBuilder> {

    PolygonShapeBuilder addVertix(float x, float y);
    
    default PolygonShapeBuilder addVertix(Vector2 vertix) {
        return addVertix(vertix.x, vertix.y);
    }
    
    default PolygonShapeBuilder vertices(Vector2[] vertices) {
        for (Vector2 vertix : vertices) {
            addVertix(vertix.x, vertix.y);
        }
        return this;
    }

    default PolygonShapeBuilder vertices(float[] vertices) {
        for (int index = 0; index < vertices.length - 1; index += 2) {
            addVertix(vertices[index], vertices[index + 1]);
        }
        return this;
    }
}