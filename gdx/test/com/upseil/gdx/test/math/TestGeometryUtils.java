package com.upseil.gdx.test.math;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.badlogic.gdx.math.Vector2;
import com.upseil.gdx.math.GeometryUtils;
import com.upseil.gdx.util.GDXArrays;

public class TestGeometryUtils {
    
    @Test
    public void testClockwisePolygonArea() {
        Vector2[] vertices = new Vector2[] {
                new Vector2(4, 6), new Vector2(4, -4), new Vector2(8, -4),
                new Vector2(8, -8), new Vector2(-4, -8), new Vector2(-4, 6)
        };
        assertThat(GeometryUtils.polygonArea(vertices), is(128.0f));
        assertThat(GeometryUtils.polygonArea(vertices, 1, 4), is(32.0f));
        
        float[] flatVertices = GDXArrays.flatten(vertices);
        assertThat(GeometryUtils.polygonArea(flatVertices), is(128.0f));
        assertThat(GeometryUtils.polygonArea(flatVertices, 2, 8), is(32.0f));
    }
    
    @Test
    public void testCounterClockwisePolygonArea() {
        Vector2[] vertices = new Vector2[] {
                new Vector2(-4,  6), new Vector2(-4, -8), new Vector2(8, -8),
                new Vector2( 8, -4), new Vector2( 4, -4), new Vector2(4,  6)
                
        };
        assertThat(GeometryUtils.polygonArea(vertices), is(128.0f));
        assertThat(GeometryUtils.polygonArea(vertices, 1, 4), is(32.0f));
        
        float[] flatVertices = GDXArrays.flatten(vertices);
        assertThat(GeometryUtils.polygonArea(flatVertices), is(128.0f));
        assertThat(GeometryUtils.polygonArea(flatVertices, 2, 8), is(32.0f));
    }
    
}
