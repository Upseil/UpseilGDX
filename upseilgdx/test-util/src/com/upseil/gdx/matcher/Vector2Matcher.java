package com.upseil.gdx.matcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Vector2Matcher extends TypeSafeMatcher<Vector2> {
    
    private final Vector2 value;
    private final float epsilon;

    public Vector2Matcher(Vector2 value, float epsilon) {
        this.value = value;
        this.epsilon = epsilon;
    }
    
    @Override
    protected boolean matchesSafely(Vector2 vector) {
        return value.epsilonEquals(vector, epsilon);
    }
    
    @Override
    protected void describeMismatchSafely(Vector2 vector, Description mismatchDescription) {
        boolean xDiffered = !MathUtils.isEqual(value.x, vector.x, epsilon);
        boolean yDiffered = !MathUtils.isEqual(value.y, vector.y, epsilon);
        if (xDiffered && yDiffered) {
            mismatchDescription.appendText("the x and y value");
        } else if (xDiffered) {
            mismatchDescription.appendText("the x value");
        } else {
            mismatchDescription.appendText("the y value");
        }
        mismatchDescription.appendText(" of ").appendValue(vector)
                           .appendText(" differed more than ").appendValue(epsilon)
                           .appendText(" from ").appendValue(value);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a vector within ").appendValue(epsilon)
                   .appendText(" of ").appendValue(value);
    }
    
    public static Matcher<Vector2> epsilonEquals(Vector2 expectedVector) {
        return new Vector2Matcher(expectedVector, MathUtils.FLOAT_ROUNDING_ERROR);
    }
    
    public static Matcher<Vector2> epsilonEquals(Vector2 expectedVector, float epsilon) {
        return new Vector2Matcher(expectedVector, epsilon);
    }
    
}
