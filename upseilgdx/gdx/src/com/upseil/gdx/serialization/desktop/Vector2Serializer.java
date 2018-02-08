package com.upseil.gdx.serialization.desktop;

import java.io.IOException;

import com.badlogic.gdx.math.Vector2;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class Vector2Serializer extends StdSerializer<Vector2> {
    private static final long serialVersionUID = 3493011201301928971L;

    public Vector2Serializer() {
        super(Vector2.class);
    }

    @Override
    public void serialize(Vector2 value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.toString());
    }
    
}
