package com.upseil.gdx.util.properties;

import java.util.HashMap;
import java.util.Map;

public class MapBasedProperties<K> implements ModifiableProperties<K> {
    
    private final Map<K, String> map;
    
    public MapBasedProperties() {
        map = new HashMap<>();
    }
    
    @Override
    public boolean contains(K key) {
        return map.containsKey(key);
    }
    
    @Override
    public Iterable<K> keys() {
        return map.keySet();
    }
    
    @Override
    public String get(K key, String defaultValue) {
        return map.getOrDefault(key, defaultValue);
    }
    
    @Override
    public boolean remove(K key) {
        return map.remove(key) != null;
    }
    
    @Override
    public void clear() {
        map.clear();
    }
    
    @Override
    public void put(K key, String value) {
        map.put(key, value);
    }
    
}
