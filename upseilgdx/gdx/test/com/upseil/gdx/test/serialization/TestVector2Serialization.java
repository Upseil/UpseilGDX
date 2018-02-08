package com.upseil.gdx.test.serialization;

import static com.upseil.gdx.matcher.Matchers.epsilonEquals;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.badlogic.gdx.math.Vector2;
import com.upseil.gdx.serialization.Mapper;
import com.upseil.gdx.serialization.desktop.DesktopMapper;

public class TestVector2Serialization {
    
    private static final Mapper<Vector2> mapper = new DesktopMapper<>(Vector2.class);
    
    private static final String vectorString = "\"(1.3,3.7)\"";
    private static final Vector2 vector = new Vector2(1.3f, 3.7f);
    
    @Test
    public void testSerialization() {
        assertThat(mapper.write(vector), is(vectorString));
        assertThat(mapper.write(null), is("null"));
    }
    
    @Test
    public void testDeserialization() {
        assertThat(mapper.read(vectorString), epsilonEquals(vector));
        assertThat(mapper.read("null"), nullValue());
    }
    
}
