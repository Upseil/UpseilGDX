package com.upseil.gdx.gwt.serialization;

import com.badlogic.gdx.math.Vector2;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

public class Vector2Deserializer extends JsonDeserializer<Vector2> {
    
    private static final Vector2Deserializer instance = new Vector2Deserializer();
    public static Vector2Deserializer getInstance() {
        return instance;
    }
    
    private Vector2Deserializer() { }
    
    @Override
    protected Vector2 doDeserialize(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
        String vectorString = reader.nextString();
        return vectorString == null ? null : new Vector2().fromString(vectorString);
    }
    
}
