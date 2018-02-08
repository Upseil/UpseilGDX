package com.upseil.gdx.serialization.desktop;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntFloatMap;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.upseil.gdx.math.ExtendedRandom;

public class Jackson {
    
    public static class Modules {
    
        private static SimpleModule All;
        
        public static Module All() {
            if (All == null) {
                All = new SimpleModule();
                
                All.addSerializer(IntFloatMap.class, new IntFloatMapSerializer());
                All.addDeserializer(IntFloatMap.class, new IntFloatMapDeserializer());
                
                All.addSerializer(new ArraySerializer());
                All.addDeserializer(Array.class, new ArrayDeserializer());
                
                All.addSerializer(ExtendedRandom.class, new ExtendedRandomSerializer());
                All.addDeserializer(ExtendedRandom.class, new ExtendedRandomDeserializer());

                All.addSerializer(Vector2.class, new Vector2Serializer());
                All.addDeserializer(Vector2.class, new Vector2Deserializer());
            }
            return All;
        }
        
        private Modules() { }
    
    }
    
    public static class Mappers {
        
        private static ObjectMapper Default;
        
        public static ObjectMapper Default() {
            if (Default == null) {
                Default = new ObjectMapper();
                Default.configure(SerializationFeature.INDENT_OUTPUT, false);
                Default.registerModule(Modules.All());
            }
            return Default;
        }
        
        private Mappers() { }
        
    }
    
    private Jackson() { }
    
}
