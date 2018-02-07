package com.upseil.gdx.gwt.serialization;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.upseil.gdx.math.ExtendedRandom;

public class ExtendedRandomSerializer extends JsonSerializer<ExtendedRandom> {
    
    private static final ExtendedRandomSerializer instance = new ExtendedRandomSerializer();
    public static ExtendedRandomSerializer getInstance() {
        return instance;
    }
    
    private ExtendedRandomSerializer() { }
    
    @Override
    protected void doSerialize(JsonWriter writer, ExtendedRandom value, JsonSerializationContext ctx, JsonSerializerParameters params) {
        writer.beginObject();
        writer.name("seed0").value(value.getState(0));
        writer.name("seed1").value(value.getState(1));
        writer.endObject();
    }
    
}
