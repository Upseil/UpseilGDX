package com.upseil.gdx.gwt.serialization;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.upseil.gdx.serialization.Writer;

public class HtmlWriter<T> implements Writer<T> {
    
    private final ObjectWriter<T> mapper;
    private final JsonSerializationContext serializationContext;

    public HtmlWriter(ObjectWriter<T> mapper, JsonSerializationContext serializationContext) {
        this.mapper = mapper;
        this.serializationContext = serializationContext;
    }

    @Override
    public String write(T object) {
        return mapper.write(object, serializationContext);
    }
    
}
