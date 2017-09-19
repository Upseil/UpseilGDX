package com.upseil.gdx.gwt.serialization;

import com.badlogic.gdx.utils.Array;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

public class ArrayDeserializer<T> extends JsonDeserializer<Array<T>> {

    public static <T> ArrayDeserializer<T> newInstance(JsonDeserializer<T> valueDeserializer) {
        return new ArrayDeserializer<>(valueDeserializer);
    }
    
    private final JsonDeserializer<T> valueDeserializer;

    private ArrayDeserializer(JsonDeserializer<T> valueDeserializer) {
        this.valueDeserializer = valueDeserializer;
    }
    
    @Override
    protected Array<T> doDeserialize(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
        JsonToken token = reader.peek();
        if (token == JsonToken.NULL) {
            return null;
        }
        
        if (token == JsonToken.BEGIN_ARRAY) {
            reader.beginArray();
            Array<T> array = new Array<>();
            while (token != JsonToken.END_ARRAY) {
                T value = valueDeserializer.deserialize(reader, ctx, params);
                array.add(value);
                token = reader.peek();
            }
            reader.endArray();
            return array;
        } else {
            throw ctx.traceError("Can't deserialize a " + Array.class.getName() + " out of " + token + " token", reader);
        }
    }
    
}
