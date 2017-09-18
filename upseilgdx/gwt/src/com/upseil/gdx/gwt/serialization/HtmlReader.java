package com.upseil.gdx.gwt.serialization;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.upseil.gdx.serialization.Reader;

public class HtmlReader<T> implements Reader<T> {
    
    private final ObjectMapper<T> mapper;
    private final JsonDeserializationContext deserializationContext;

    public HtmlReader(ObjectMapper<T> mapper, JsonDeserializationContext deserializationContext) {
        this.mapper = mapper;
        this.deserializationContext = deserializationContext;
    }

    @Override
    public T read(String data) {
        return mapper.read(data, deserializationContext);
    }
    
}
