package com.upseil.gdx.gwt.serialization;

import com.badlogic.gdx.math.Vector2;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

public class Vector2Serializer extends JsonSerializer<Vector2> {
    
    private static final Vector2Serializer instance = new Vector2Serializer();
    public static Vector2Serializer getInstance() {
        return instance;
    }
    
    private Vector2Serializer() { }
    
    @Override
    protected void doSerialize(JsonWriter writer, Vector2 value, JsonSerializationContext ctx, JsonSerializerParameters params) {
        writer.value(value.toString());
    }
    
}
