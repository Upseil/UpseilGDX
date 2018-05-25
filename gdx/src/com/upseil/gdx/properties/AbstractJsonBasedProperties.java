package com.upseil.gdx.properties;

import java.util.Collection;
import java.util.HashSet;

import com.badlogic.gdx.utils.JsonValue;
import com.upseil.gdx.util.properties.Properties;

public abstract class AbstractJsonBasedProperties<K> implements Properties<K> {
    
    protected final JsonValue json;
    
    private Collection<K> keys;
    
    public AbstractJsonBasedProperties(JsonValue json) {
        this.json = json;
    }

    @Override
    public boolean contains(K key) {
        return json.has(keyToString(key));
    }
    
    @Override
    public Iterable<K> keys() {
        if (keys == null) {
            keys = new HashSet<>();
            for (JsonValue child : json) {
                keys.add(stringToKey(child.name));
            }
        }
        return keys;
    }
    
    @Override
    public String get(K key, String defaultValue) {
        return json.getString(keyToString(key), defaultValue);
    }
    
    @Override
    public int getInt(K key, int defaultValue) {
        return json.getInt(keyToString(key), defaultValue);
    }

    @Override
    public long getLong(K key, long defaultValue) {
        return json.getLong(keyToString(key), defaultValue);
    }

    @Override
    public float getFloat(K key, float defaultValue) {
        return json.getFloat(keyToString(key), defaultValue);
    }

    @Override
    public double getDouble(K key, double defaultValue) {
        return json.getDouble(keyToString(key), defaultValue);
    }

    @Override
    public boolean getBoolean(K key, boolean defaultValue) {
        return json.getBoolean(keyToString(key), defaultValue);
    }

    protected abstract String keyToString(K key);
    protected abstract K stringToKey(String string);
    
}
