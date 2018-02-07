package com.upseil.gdx.serialization.desktop;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.upseil.gdx.math.ExtendedRandom;

public class ExtendedRandomSerializer extends StdSerializer<ExtendedRandom> {
    private static final long serialVersionUID = -2848501092551944729L;

    public ExtendedRandomSerializer() {
        super(ExtendedRandom.class);
    }

    @Override
    public void serialize(ExtendedRandom value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("seed0", value.getState(0));
        gen.writeNumberField("seed1", value.getState(1));
        gen.writeEndObject();
    }
    
}
