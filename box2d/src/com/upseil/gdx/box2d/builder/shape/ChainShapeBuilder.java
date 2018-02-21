package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;

public interface ChainShapeBuilder extends ShapeBuilder<ChainShape> {
    
    ChainShapeBuilder withRadius(float radius);
    
    ChainShapeBuilder asLoop(boolean isLoop);
    default ChainShapeBuilder asLoop() {
        return asLoop(true);
    }
    default ChainShapeBuilder notAsLoop() {
        return asLoop(false);
    }
    
    ChainShapeBuilder addFirst(float x, float y);
    default ChainShapeBuilder addFirst(Vector2 vertix) {
        return addFirst(vertix.x, vertix.y);
    }
    
    ChainShapeBuilder addLast(float x, float y);
    default ChainShapeBuilder addLast(Vector2 vertix) {
        return addLast(vertix.x, vertix.y);
    }
    
    
    default ChainShapeBuilder withVertices(Vector2[] vertices) {
        for (Vector2 vertix : vertices) {
            addLast(vertix.x, vertix.y);
        }
        return this;
    }

    default ChainShapeBuilder withVertices(float[] vertices) {
        for (int index = 0; index < vertices.length - 1; index += 2) {
            addLast(vertices[index], vertices[index + 1]);
        }
        return this;
    }
    
}
