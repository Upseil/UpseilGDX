package com.upseil.gdx.artemis;

import com.badlogic.gdx.utils.JsonValue;
import com.upseil.gdx.properties.EnumerizedJsonBasedProperties;

public final class ArtemisConfigs {
    
    public static enum SaveConfigValues {
        SaveStoreName, AutoSaveInterval, AutoSaveSlot, SaveSlots, SlotPrefix, TimeSuffix, NameSuffix
    }

    public static class SaveConfig extends EnumerizedJsonBasedProperties<SaveConfigValues> {

        public SaveConfig(JsonValue json) {
            super(json, SaveConfigValues.class);
        }
        
    }
    
    private ArtemisConfigs() { }
    
}
