package com.upseil.gdx.serialization.desktop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.upseil.gdx.serialization.CompressingMapper;

public class DesktopSavegameMapper<T> extends CompressingMapper<T> {
    
    private final ObjectMapper mapper;
    private final Class<T> type;
    
    public DesktopSavegameMapper(Class<T> type) {
        this(defaultMapper(), type);
    }
    
    private static ObjectMapper defaultMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        return mapper;
    }

    public DesktopSavegameMapper(ObjectMapper mapper, Class<T> type) {
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
