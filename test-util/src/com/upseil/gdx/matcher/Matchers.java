package com.upseil.gdx.matcher;

import org.hamcrest.Matcher;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Matchers {
    
    public static Matcher<Vector2> epsilonEquals(Vector2 expectedVector) {
        return Vector2Matcher.epsilonEquals(expectedVector, MathUtils.FLOAT_ROUNDING_ERROR);
    }
    
    public static Matcher<Vector2> epsilonEquals(Vector2 expectedVector, float epsilon) {
        return Vector2Matcher.epsilonEquals(expectedVector, epsilon);
    }
    
    private Matchers() { }
    
}
