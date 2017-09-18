package com.upseil.gdx.serialization.desktop;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upseil.gdx.serialization.Reader;

public class DesktopReader<T> implements Reader<T> {
    
    private final ObjectMapper mapper;
    private final Class<T> type;

    public DesktopReader(ObjectMapper mapper, Class<T> type) {
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
