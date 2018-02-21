package com.upseil.gdx.gwt.serialization;

import java.util.HashMap;
import java.util.Map;

public class ClassCache {
    
    private final Map<String, Class<?>> cache;
    
    public ClassCache() {
        cache = new HashMap<>();
    }
    
    public <T> Class<T> get(String className) {
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) cache.get(className);
        if (clazz == null) {
            throw new IllegalArgumentException("No class cached with name " + className);
        }
        return clazz;
    }

    public void put(Class<?> clazz) {
        cache.put(getKey(clazz), clazz);
    }
    
    public static String getKey(Class<?> clazz) {
        return clazz.getName();
    }
    
}
