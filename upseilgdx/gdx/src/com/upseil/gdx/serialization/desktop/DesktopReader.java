package com.upseil.gdx.serialization.desktop;

import java.io.IOException;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.upseil.gdx.serialization.Reader;

public class DesktopReader<T> implements Reader<T> {
    
    private final ObjectMapper mapper;
    private final JavaType type;

    public DesktopReader(Class<T> type) {
        this(Jackson.Mappers.Default(), type);
    }

    public DesktopReader(ObjectMapper mapper, Class<T> type) {
        this(mapper, TypeFactory.defaultInstance().constructType(type));
    }
    
    public DesktopReader(JavaType type) {
        this(Jackson.Mappers.Default(), type);
    }

    public DesktopReader(ObjectMapper mapper, JavaType type) {
        this.mapper = mapper;
        this.type = type;
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
