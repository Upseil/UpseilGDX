package com.upseil.gdx.util.properties;

import java.util.HashMap;
import java.util.Map;

public class CachingProperties<K> implements Properties<K> {
    
    private final Properties<K> properties;
    private final Map<K, Object> cache;
    
    public CachingProperties(Properties<K> properties) {
        this.properties = properties;
        cache = new HashMap<>();
    }
    
    public void reset(K key) {
        cache.remove(key);
    }
    
    public void resetAll() {
        cache.clear();
    }

    @Override
    public boolean contains(K key) {
        return properties.contains(key);
    }
    
    @Override
    public Iterable<K> keys() {
        return properties.keys();
    }
    
    @Override
    public String get(K key, String defaultValue) {
        String value = defaultValue;
        Object cachedValue = cache.get(key);
        if (cachedValue != null && cachedValue instanceof String) {
            value = (String) cachedValue;
        } else if (properties.contains(key)) {
            value = properties.get(key, defaultValue);
            cache.put(key, value);
        }
        return value;
    }

    @Override
    public int getInt(K key, int defaultValue) {
        int value = defaultValue;
        Object cachedValue = cache.get(key);
        if (cachedValue != null && cachedValue instanceof Double) {
            value = ((Double) cachedValue).intValue();
        } else if (properties.contains(key)) {
            value = properties.getInt(key, defaultValue);
            cache.put(key, new Double(value));
        }
        return value;
    }

    @Override
    public long getLong(K key, long defaultValue) {
        long value = defaultValue;
        Object cachedValue = cache.get(key);
        if (cachedValue != null && cachedValue instanceof Long) {
            value = (Long) cachedValue;
        } else if (properties.contains(key)) {
            value = properties.getLong(key, defaultValue);
            cache.put(key, value);
        }
        return value;
    }

    @Override
    public float getFloat(K key, float defaultValue) {
        float value = defaultValue;
        Object cachedValue = cache.get(key);
        if (cachedValue != null && cachedValue instanceof Double) {
            value = ((Double) cachedValue).floatValue();
        } else if (properties.contains(key)) {
            value = properties.getFloat(key, defaultValue);
            cache.put(key, new Double(value));
        }
        return value;
    }

    @Override
    public double getDouble(K key, double defaultValue) {
        double value = defaultValue;
        Object cachedValue = cache.get(key);
        if (cachedValue != null && cachedValue instanceof Double) {
            value = (Double) cachedValue;
        } else if (properties.contains(key)) {
            value = properties.getDouble(key, defaultValue);
            cache.put(key, value);
        }
        return value;
    }

    @Override
    public boolean getBoolean(K key, boolean defaultValue) {
        boolean value = defaultValue;
        Object cachedValue = cache.get(key);
        if (cachedValue != null && cachedValue instanceof Boolean) {
            value = (Boolean) cachedValue;
        } else if (properties.contains(key)) {
            value = properties.getBoolean(key, defaultValue);
            cache.put(key, value);
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E extends Enum<E>> E getEnum(K key, Class<E> type, E defaultValue) {
        E value = defaultValue;
        Object cachedValue = cache.get(key);
        if (cachedValue != null && cachedValue instanceof Enum) {
            value = (E) cachedValue;
        } else if (properties.contains(key)) {
            value = properties.getEnum(key, type, defaultValue);
            cache.put(key, value);
        }
        return value;
    }
    
}
