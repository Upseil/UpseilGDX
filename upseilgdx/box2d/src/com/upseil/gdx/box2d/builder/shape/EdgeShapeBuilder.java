package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.EdgeShape;

public interface EdgeShapeBuilder extends ShapeBuilder<EdgeShape> {
    
    EdgeShapeBuilder withRadius(float radius);

    EdgeShapeBuilder withVertix1(float x, float y);
    EdgeShapeBuilder withGhostVertix1(float x, float y);
    EdgeShapeBuilder withVertix2(float x, float y);
    EdgeShapeBuilder withGhostVertix2(float x, float y);
    
    default EdgeShapeBuilder withVertix1(float x, float y, float ghostX, float ghostY) {
        withVertix1(x, y);
        withGhostVertix1(ghostX, ghostY);
        return this;
    }
    
    default EdgeShapeBuilder withVertix1(Vector2 vertix, Vector2 ghostVertix) {
        return withVertix1(vertix.x, vertix.y, ghostVertix.x, ghostVertix.y);
    }
    
    default EdgeShapeBuilder withVertix2(float x, float y, float ghostX, float ghostY) {
        withVertix2(x, y);
        withGhostVertix2(ghostX, ghostY);
        return this;
    }
    
    default EdgeShapeBuilder withVertix2(Vector2 vertix, Vector2 ghostVertix) {
        return withVertix2(vertix.x, vertix.y, ghostVertix.x, ghostVertix.y);
    }
    
    default EdgeShapeBuilder set(float x1, float y1, float x2, float y2) {
        withVertix1(x1, y1);
        withVertix2(x2, y2);
        return this;
    }
    
    default EdgeShapeBuilder set(Vector2 vertix1, Vector2 vertix2) {
        return set(vertix1.x, vertix1.y, vertix2.x, vertix2.y);
    }
    
    default EdgeShapeBuilder set(float x1, float y1, float ghostX1, float ghostY1, float x2, float y2, float ghostX2, float ghostY2) {
        withVertix1(x1, y1, ghostX1, ghostY1);
        withVertix2(x2, y2, ghostX2, ghostY2);
        return this;
    }
    
    default EdgeShapeBuilder set(Vector2 vertix1, Vector2 ghostVertix1, Vector2 vertix2, Vector2 ghostVertix2) {
        return set(vertix1.x, vertix2.y, ghostVertix1.x, ghostVertix1.y, vertix2.x, vertix2.y, ghostVertix2.x, ghostVertix2.y);
    }
    
}
