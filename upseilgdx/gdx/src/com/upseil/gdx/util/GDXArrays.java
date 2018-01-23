package com.upseil.gdx.util;

import com.badlogic.gdx.math.Vector2;

public class GDXArrays {
    
    private GDXArrays() { }
    
    public static float[] flatten(Vector2[] vectors) {
        float[] coordinates = new float[vectors.length * 2];
        for (int i = 0, j = 0; i < coordinates.length; i += 2, j++) {
            coordinates[i] = vectors[j].x;
            coordinates[i + 1] = vectors[j].y;
        }
        return coordinates;
    }
    
}
