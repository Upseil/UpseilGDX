package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public interface PolygonShapeBuilder extends ShapeBuilder<PolygonShape> {
    
    PolygonShapeBuilder withRadius(float radius);

    PolygonShapeBuilder addVertix(float x, float y);
    
    default PolygonShapeBuilder addVertix(Vector2 vertix) {
        return addVertix(vertix.x, vertix.y);
    }
    
    default PolygonShapeBuilder withVertices(Vector2[] vertices) {
        for (Vector2 vertix : vertices) {
            addVertix(vertix.x, vertix.y);
        }
        return this;
    }

    default PolygonShapeBuilder withVertices(float[] vertices) {
        for (int index = 0; index < vertices.length - 1; index += 2) {
            addVertix(vertices[index], vertices[index + 1]);
        }
        return this;
    }
}