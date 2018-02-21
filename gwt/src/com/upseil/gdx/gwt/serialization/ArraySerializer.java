package com.upseil.gdx.gwt.serialization;

import com.badlogic.gdx.utils.Array;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

public class ArraySerializer<T> extends JsonSerializer<Array<T>> {

    public static <T> ArraySerializer<T> newInstance(JsonSerializer<T> valueSerializer) {
        return new ArraySerializer<>(valueSerializer);
    }
    
    private final JsonSerializer<T> valueSerializer;

    private ArraySerializer(JsonSerializer<T> valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    @Override
    protected void doSerialize(JsonWriter writer, Array<T> array, JsonSerializationContext ctx, JsonSerializerParameters params) {
        if (writer.getSerializeNulls() && array == null) {
            writer.nullValue();
            return;
        }
        
        writer.beginArray();
        for (T value : array) {
            valueSerializer.serialize(writer, value, ctx, params);
        }
        writer.endArray();
    }
    
}
