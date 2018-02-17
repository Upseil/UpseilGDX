package com.upseil.gdx.test.config;

import com.badlogic.gdx.utils.ObjectMap;
import com.upseil.gdx.config.RawConfig;

public class MutableRawConfig extends RawConfig {
    
    private final ObjectMap<String, Object> values;
    
    public MutableRawConfig() {
        this(null);
    }

    public MutableRawConfig(RawConfig parent) {
        super(parent, null);
        values = new ObjectMap<>();
    }
    
    public void setValue(String key, Object value) {
        values.put(key, value);
    }
    
    @Override
    public float getFloat(String key) {
        return (float) values.get(key, -1);
    }

    @Override
    public double getDouble(String key) {
        return (double) values.get(key, -1);
    }

    @Override
    public int getInt(String key) {
        return (int) values.get(key, -1);
    }

    @Override
    public boolean getBoolean(String key) {
        return (boolean) values.get(key, false);
    }

    @Override
    public String getString(String key) {
        return (String) values.get(key);
    }

    @Override
    public RawConfig getChild(String key) {
        return (RawConfig) values.get(key);
    }
    
    @Override
    public boolean hasNode(String key) {
        return values.containsKey(key);
    }

    @Override
    public Iterable<RawConfig> getChildren() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean isNull() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean isArray() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int size() {
        throw new UnsupportedOperationException();
    }
    
}
