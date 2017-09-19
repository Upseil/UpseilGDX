package com.upseil.gdx.gwt.serialization;

import com.badlogic.gdx.utils.IntFloatMap;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

public class IntFloatMapDeserializer extends JsonDeserializer<IntFloatMap> {
    
    private static final IntFloatMapDeserializer instance = new IntFloatMapDeserializer();
    public static IntFloatMapDeserializer getInstance() {
        return instance;
    }
    
    private IntFloatMapDeserializer() {
    }
    
    @Override
    protected IntFloatMap doDeserialize(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
        JsonToken token = reader.peek();
        if (token == JsonToken.NULL) {
            return null;
        }
        
        if (token == JsonToken.BEGIN_OBJECT) {
            reader.beginObject();
            IntFloatMap map = new IntFloatMap();
            while (token != JsonToken.END_OBJECT) {
                int key = Integer.parseInt(reader.nextName());
                map.put(key, reader.nextNumber().floatValue());
                token = reader.peek();
            }
            reader.endObject();
            return map;
        } else {
            throw ctx.traceError("Can't deserialize a " + IntFloatMap.class.getName() + " out of " + token + " token", reader);
        }
    }
    
}
