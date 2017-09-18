package com.upseil.gdx.serialization.desktop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upseil.gdx.serialization.Writer;

public class DesktopWriter<T> implements Writer<T> {
    
    private final ObjectMapper mapper;

    public DesktopWriter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public String write(T object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    
}
