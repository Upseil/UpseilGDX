package com.upseil.gdx.serialization.desktop;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntFloatMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.upseil.gdx.serialization.CompressingMapper;

public class DesktopSavegameMapper<T> extends CompressingMapper<T> {
    
    private static ObjectMapper defaultMapper;
    
    private final ObjectMapper mapper;
    private final Class<T> type;
    
    public DesktopSavegameMapper(Class<T> type) {
        this(defaultMapper(), type);
    }
    
    private static ObjectMapper defaultMapper() {
        if (defaultMapper == null) {
            defaultMapper = new ObjectMapper();
            defaultMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
            
            SimpleModule module = new SimpleModule();
            module.addSerializer(IntFloatMap.class, new IntFloatMapSerializer());
            module.addDeserializer(IntFloatMap.class, new IntFloatMapDeserializer());
            module.addDeserializer(Array.class, new ArrayDeserializer());
            defaultMapper.registerModule(module);
        }
        return defaultMapper;
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
