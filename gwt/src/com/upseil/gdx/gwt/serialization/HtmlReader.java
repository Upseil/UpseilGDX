package com.upseil.gdx.gwt.serialization;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.ObjectReader;
import com.upseil.gdx.serialization.Reader;

public class HtmlReader<T> implements Reader<T> {
    
    private final ObjectReader<T> mapper;
    private final JsonDeserializationContext deserializationContext;

    public HtmlReader(ObjectReader<T> mapper, JsonDeserializationContext deserializationContext) {
        this.mapper = mapper;
        this.deserializationContext = deserializationContext;
    }

    @Override
    public T read(String data) {
        return mapper.read(data, deserializationContext);
    }
    
}
