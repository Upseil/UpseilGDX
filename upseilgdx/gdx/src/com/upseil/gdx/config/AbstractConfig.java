package com.upseil.gdx.config;

import com.upseil.gdx.util.DoubleBasedExpression;

public abstract class AbstractConfig {
    
    private final RawConfig rawConfig;
    
    public AbstractConfig(String path) {
        this(RawConfig.loadConfig(path));
    }

    public AbstractConfig(RawConfig rawConfig) {
        this.rawConfig = rawConfig;
    }
    
    protected RawConfig getRawConfig() {
        return rawConfig;
    }
    
    protected float getFloat(String key) {
        return rawConfig.getFloat(key);
    }
    
    protected double getDouble(String key) {
        return rawConfig.getDouble(key);
    }
    
    protected int getInt(String key) {
        return rawConfig.getInt(key);
    }

    protected boolean getBoolean(String key) {
        return rawConfig.getBoolean(key);
    }
    
    protected String getString(String key) {
        return rawConfig.getString(key);
    }
    
    protected DoubleBasedExpression getExpression(String key) {
        return rawConfig.getExpression(key);
    }
    
    protected <T extends Enum<T>> T getEnum(String key, Class<T> type) {
        return rawConfig.getEnum(key, type);
    }
    
}
