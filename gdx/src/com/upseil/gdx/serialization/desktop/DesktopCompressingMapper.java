package com.upseil.gdx.serialization.desktop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upseil.gdx.serialization.CompressingMapper;

public class DesktopCompressingMapper<T> extends CompressingMapper<T> {
    
    private final ObjectMapper mapper;
    private final Class<T> type;
    
    public DesktopCompressingMapper(Class<T> type) {
        this(Jackson.Mappers.Default(), type);
    }

    public DesktopCompressingMapper(ObjectMapper mapper, Class<T> type) {
        this.mapper = mapper;
        this.type = type;
    }

    @Override
    protected String writeUncompressed(T savegame) throws Exception {
        return mapper.writeValueAsString(savegame);
    }
    
    @Override
    protected T readUncompressed(String uncompressedData) throws Exception {
        return mapper.readValue(uncompressedData, type);
    }
    
}
