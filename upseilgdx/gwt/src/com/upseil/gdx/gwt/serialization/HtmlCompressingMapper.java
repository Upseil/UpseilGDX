package com.upseil.gdx.gwt.serialization;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.upseil.gdx.serialization.CompressingMapper;

public class HtmlCompressingMapper<T> extends CompressingMapper<T> {
    
    private final ObjectMapper<T> mapper;
    private final JsonSerializationContext serializationContext;
    private final JsonDeserializationContext deserializationContext;

    public HtmlCompressingMapper(ObjectMapper<T> mapper, JsonSerializationContext serializationContext, JsonDeserializationContext deserializationContext) {
        this.mapper = mapper;
        this.serializationContext = serializationContext;
        this.deserializationContext = deserializationContext;
    }

    @Override
    protected String writeUncompressed(T game) throws Exception {
        return mapper.write(game, serializationContext);
    }

    @Override
    protected T readUncompressed(String uncompressedData) throws Exception {
        return mapper.read(uncompressedData, deserializationContext);
    }
    
}
