package com.upseil.gdx.artemis.config;

import com.upseil.gdx.config.AbstractConfig;
import com.upseil.gdx.config.RawConfig;

public class SaveConfig extends AbstractConfig {

    public SaveConfig(RawConfig rawConfig) {
        super(rawConfig);
    }
    
    public String getSaveStoreName() {
        return getString("saveStoreName");
    }
    
    public float getAutoSaveInterval() {
        return getFloat("autoSaveInterval");
    }
    
    public String getAutoSaveSlot() {
        return getString("autoSaveSlot");
    }
    
    public int getSaveSlots() {
        return getInt("saveSlots");
    }
    
    public String getSaveSlotPrefix() {
        return getString("slot");
    }
    
    public String getTimeSuffix() {
        return getString("timeSuffix");
    }
    
    public String getNameSuffix() {
        return getString("nameSuffix");
    }
    
}