package com.upseil.gdx.serialization.desktop;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.upseil.gdx.serialization.Mapper;

public class DesktopMapper<T> implements Mapper<T> {
    
    private final ObjectMapper mapper;
    private final JavaType type;

    public DesktopMapper(ObjectMapper mapper, Class<T> type) {
        this(mapper, TypeFactory.defaultInstance().constructType(type));
    }

    public DesktopMapper(ObjectMapper mapper, JavaType type) {
        this.mapper = mapper;
        this.type = type;
    }

    @Override
    public String write(T object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T read(String data) {
        try {
            return mapper.readValue(data, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
