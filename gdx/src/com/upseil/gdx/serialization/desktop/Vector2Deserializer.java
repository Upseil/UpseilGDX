package com.upseil.gdx.serialization.desktop;

import java.io.IOException;

import com.badlogic.gdx.math.Vector2;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class Vector2Deserializer extends StdDeserializer<Vector2> {
    private static final long serialVersionUID = 2659038391897896078L;

    public Vector2Deserializer() {
        super(Vector2.class);
    }

    @Override
    public Vector2 deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String vectorString = _parseString(p, ctxt);
        return vectorString == null ? null : new Vector2().fromString(vectorString);
    }
    
}
