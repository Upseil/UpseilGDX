package com.upseil.gdx.config;

public class AbstractConfig {
    
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
    
}
