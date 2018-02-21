package com.upseil.gdx.gwt.serialization;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.upseil.gdx.serialization.Mapper;

public class HtmlMapper<T> implements Mapper<T> {
    
    private final ObjectMapper<T> mapper;
    private final JsonSerializationContext serializationContext;
    private final JsonDeserializationContext deserializationContext;

    public HtmlMapper(ObjectMapper<T> mapper, JsonSerializationContext serializationContext, JsonDeserializationContext deserializationContext) {
        this.mapper = mapper;
        this.serializationContext = serializationContext;
        this.deserializationContext = deserializationContext;
    }

    @Override
    public String write(T object) {
        return mapper.write(object, serializationContext);
    }

    @Override
    public T read(String data) {
        return mapper.read(data, deserializationContext);
    }
    
}
