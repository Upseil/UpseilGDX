package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public interface PolygonShapeBuilder extends ShapeBuilder<PolygonShape> {

    PolygonShapeBuilder at(float centerX, float centerY);
    default PolygonShapeBuilder at(Vector2 centerPosition) {
        return at(centerPosition.x, centerPosition.y);
    }
    
    PolygonShapeBuilder withRadius(float radius);

    PolygonShapeBuilder addVertix(float x, float y);
    
    /**
     * Adjusts all vertices so that the origin matches the shapes center.
     */
    PolygonShapeBuilder normalizeOriginToCenter();
    
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