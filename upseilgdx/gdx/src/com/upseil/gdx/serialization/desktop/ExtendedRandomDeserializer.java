package com.upseil.gdx.serialization.desktop;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.upseil.gdx.math.ExtendedRandom;
import com.upseil.gdx.math.ExtendedRandomXS128;

public class ExtendedRandomDeserializer extends StdDeserializer<ExtendedRandom> {
    private static final long serialVersionUID = -3718978387133829718L;

    public ExtendedRandomDeserializer() {
        super(ExtendedRandom.class);
    }
    
    private long seed0;
    private long seed1;

    @Override
    public ExtendedRandom deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        if (p.currentTokenId() == JsonTokenId.ID_NULL) {
            return null;
        }
        
        if (p.currentTokenId() != JsonTokenId.ID_START_OBJECT) {
            throw new JsonParseException(p, "Can't deserialize a " + ExtendedRandom.class.getName() + " out of " + p.currentToken() + " token");
        }

        seed0 = -1;
        seed1 = -1;
        
        parseSeed(p, ctxt);
        parseSeed(p, ctxt);
        
        if (p.nextToken() != JsonToken.END_OBJECT) {
            throw new JsonParseException(p, "Expected " + JsonToken.END_OBJECT + " but got " + p.currentToken());
        }
        
        return new ExtendedRandomXS128(seed0, seed1);
    }

    private void parseSeed(JsonParser p, DeserializationContext ctxt) throws IOException {
        String fieldName = p.nextFieldName();
        if (fieldName == null) {
            throw new JsonParseException(p, "Expected " + JsonToken.FIELD_NAME + " but got " + p.currentToken());
        }

        boolean isSeed0 = fieldName.equals("seed0");
        boolean isSeed1 = fieldName.equals("seed1");
        if (!isSeed0 && !isSeed1) {
            throw new JsonParseException(p, "Unexpected field name " + fieldName + ". Expected seed0 or seed1.");
        }
        
        p.nextToken();
        long seed = _parseLongPrimitive(p, ctxt);
        if (isSeed0) seed0 = seed;
        else         seed1 = seed;
    }
    
}
