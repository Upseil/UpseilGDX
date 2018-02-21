package com.upseil.gdx.serialization.desktop;

import java.io.IOException;

import com.badlogic.gdx.utils.IntFloatMap;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class IntFloatMapDeserializer extends StdDeserializer<IntFloatMap> {
    private static final long serialVersionUID = -1829232448733651628L;

    public IntFloatMapDeserializer() {
        super(IntFloatMap.class);
    }

    @Override
    public IntFloatMap deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        if (p.currentToken() == JsonToken.VALUE_NULL) {
            return null;
        }
        
        if (p.currentToken() == JsonToken.START_OBJECT) {
            IntFloatMap map = new IntFloatMap();
            while (p.nextToken() != JsonToken.END_OBJECT) {
                int key = Integer.parseInt(p.getCurrentName());
                p.nextToken();
                map.put(key, p.getFloatValue());
            }
            return map;
        } else {
            throw new JsonParseException(p, "Can't deserialize a " + IntFloatMap.class.getName() + " out of " + p.currentToken() + " token");
        }
    }
    
}
