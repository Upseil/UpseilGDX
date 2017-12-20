package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public interface ChainedPolygonShapeBuilder<P> extends PolygonShapeBuilder, ChainedShapeBuilder<PolygonShape, P> {
    
    ChainedPolygonShapeBuilder<P> withRadius(float radius);

    ChainedPolygonShapeBuilder<P> addVertix(float x, float y);
    
    default ChainedPolygonShapeBuilder<P> addVertix(Vector2 vertix) {
        return addVertix(vertix.x, vertix.y);
    }
    
    default ChainedPolygonShapeBuilder<P> vertices(Vector2[] vertices) {
        for (Vector2 vertix : vertices) {
            addVertix(vertix.x, vertix.y);
        }
        return this;
    }

    default ChainedPolygonShapeBuilder<P> vertices(float[] vertices) {
        for (int index = 0; index < vertices.length - 1; index += 2) {
            addVertix(vertices[index], vertices[index + 1]);
        }
        return this;
    }
    
}
