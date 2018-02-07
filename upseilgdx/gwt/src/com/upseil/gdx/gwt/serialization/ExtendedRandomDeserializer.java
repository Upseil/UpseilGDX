package com.upseil.gdx.gwt.serialization;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;
import com.upseil.gdx.math.ExtendedRandom;
import com.upseil.gdx.math.ExtendedRandomXS128;

public class ExtendedRandomDeserializer extends JsonDeserializer<ExtendedRandom> {
    
    private static final ExtendedRandomDeserializer instance = new ExtendedRandomDeserializer();
    public static ExtendedRandomDeserializer getInstance() {
        return instance;
    }
    
    private ExtendedRandomDeserializer() { }
    
    private long seed0 = -1;
    private long seed1 = -1;
    
    @Override
    protected ExtendedRandom doDeserialize(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
        JsonToken firstToken = reader.peek();
        if (firstToken == JsonToken.NULL) {
            return null;
        }

        if (firstToken != JsonToken.BEGIN_OBJECT) {
            throw ctx.traceError("Can't deserialize a " + ExtendedRandomDeserializer.class.getName() + " out of " + firstToken + " token", reader);
        }

        seed0 = -1;
        seed1 = -1;

        parseSeed(reader, ctx);
        parseSeed(reader, ctx);
        
        if (reader.peek() != JsonToken.END_OBJECT) {
            ctx.traceError("Expected " + JsonToken.END_OBJECT + " but got " + reader.peek());
        }
        
        return new ExtendedRandomXS128(seed0, seed1);
    }

    private void parseSeed(JsonReader reader, JsonDeserializationContext ctx) {
        String fieldName = reader.nextName();
        boolean isSeed0 = fieldName.equals("seed0");
        boolean isSeed1 = fieldName.equals("seed1");
        if (!isSeed0 && !isSeed1) {
            throw ctx.traceError("Unexpected field name " + fieldName + ". Expected seed0 or seed1.");
        }
        
        long seed = reader.nextLong();
        if (isSeed0) seed0 = seed;
        else         seed1 = seed;
    }
    
}
