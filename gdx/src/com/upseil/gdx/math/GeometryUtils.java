package com.upseil.gdx.math;

import com.badlogic.gdx.math.Vector2;

public class GeometryUtils {
    
    public static float polygonArea(Vector2[] vertices) {
        return polygonArea(vertices, 0, vertices.length);
    }
    
    public static float polygonArea(Vector2[] vertices, int offset, int length) {
        float area = 0;
        int bound = offset + length;
        Vector2 vertexB = vertices[bound - 1];
        for (int i = offset; i < bound; i++) {
            Vector2 vertexA = vertices[i];
            area += (vertexB.x + vertexA.x) * (vertexB.y - vertexA.y);
            vertexB = vertexA;
        }
        return Math.abs(area / 2);
    }
    
    public static float polygonArea(float[] vertices) {
        return polygonArea(vertices, 0, vertices.length);
    }
    
    public static float polygonArea(float[] vertices, int offset, int length) {
        float area = 0;
        int bound = offset + length;
        int j = bound - 2;
        for (int i = offset; i < bound; i += 2) {
            area += (vertices[j] + vertices[i]) * (vertices[j+1] - vertices[i+1]);
            j = i;
        }
        return Math.abs(area / 2);
    }

    private GeometryUtils() { }
    
}
