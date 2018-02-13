package com.upseil.gdx.artemis.system;

import java.util.function.Consumer;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Clipboard;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.TimeUtils;
import com.upseil.gdx.artemis.ArtemisApplicationAdapter;
import com.upseil.gdx.config.AbstractConfig;
import com.upseil.gdx.config.RawConfig;
import com.upseil.gdx.serialization.Writer;

public abstract class AbstractSaveSystem<T> extends BaseSystem {
    
    private final Writer<T> mapper;
    private final Clipboard systemAccessClipboard;

    private float accumulatedDelta;
    private boolean isAutoSaving;
    private boolean isScheduled;
    
    private final Preferences saveStore;
    private final String autoSaveSlot;
    private float autoSaveInterval;
    private final int saveSlots;
    private final String saveSlotPrefix;
    private final String timeSuffix;
    private final String nameSuffix;
    
    private final Array<Consumer<String>> exportCallbacks;
    private final ObjectSet<String> slotsToSave;
    
    public AbstractSaveSystem(Writer<T> mapper, Clipboard systemAccessClipboard, AbstractSaveSystem.Config config) {
        this.mapper = mapper;
        this.systemAccessClipboard = systemAccessClipboard;
        
        saveStore = Gdx.app.getPreferences(config.getSaveStoreName());
        autoSaveSlot = config.getAutoSaveSlot();
        autoSaveInterval = config.getAutoSaveInterval();
        saveSlots = config.getSaveSlots();
        saveSlotPrefix = config.getSaveSlotPrefix();
        timeSuffix = config.getTimeSuffix();
        nameSuffix = config.getNameSuffix();
        
        exportCallbacks = new Array<>(4);
        slotsToSave = new ObjectSet<>();
    }
    
    public void saveToAutoSlot() {
        scheduleSave(autoSaveSlot);
        accumulatedDelta = 0;
    }
    
    public void saveToSlot(int slotNumber) {
        ensureSaveSlotsAreSupported();
        scheduleSave(getSlotKey(slotNumber));
    }

    protected void scheduleSave(String slotKey) {
        isScheduled = true;
        slotsToSave.add(slotKey);
    }
    
    public void exportSave(Consumer<String> callback) {
        exportCallbacks.add(callback);
        isScheduled = true;
    }
    
    public void deleteSave(int slotNumber) {
        ensureSaveSlotsAreSupported();
        deleteSave(getSlotKey(slotNumber));
    }

    public void deleteSave(String slotKey) {
        saveStore.remove(slotKey);
        saveStore.remove(getNameKey(slotKey));
        saveStore.remove(getTimeKey(slotKey));
        saveStore.flush();
    }

    public void reset(boolean resetAll) {
        if (resetAll) {
            saveStore.clear();
        } else {
            saveStore.remove(autoSaveSlot);
            saveStore.remove(getTimeKey(autoSaveSlot));
        }
        saveStore.flush();
        exportCallbacks.clear();
        slotsToSave.clear();
        ((ArtemisApplicationAdapter) Gdx.app.getApplicationListener()).reset();
    }

    @Override
    protected boolean checkProcessing() {
        boolean autoSave = false;
        if (isAutoSaving && autoSaveInterval > 0) {
            accumulatedDelta += world.getDelta();
            if (accumulatedDelta >= autoSaveInterval) {
                accumulatedDelta -= (int) (accumulatedDelta / autoSaveInterval) * autoSaveInterval;
                slotsToSave.add(autoSaveSlot);
                autoSave = true;
            } 
        }
        
        return autoSave || isScheduled;
    }
    
    @Override
    protected void processSystem() {
        T game = getSavegame();
        String data = mapper.write(game);
        
        if (data == null) {
            onSavingFailed();
        } else {
            if (slotsToSave.size > 0) {
                boolean saveTimestamps = timeSuffix != null && !timeSuffix.isEmpty();
                long timestamp = TimeUtils.millis();
                for (String slot : slotsToSave) {
                    saveStore.putString(slot, data);
                    if (saveTimestamps) saveStore.putLong(getTimeKey(slot), timestamp);
                }
                saveStore.flush();
            }
            
            if (exportCallbacks.size > 0) {
                for (Consumer<String> callback : exportCallbacks) {
                    callback.accept(data);
                }
            }
            onSavingSucceeded();
        }
        
        if (world.getDelta() > autoSaveInterval) {
            accumulatedDelta = 0;
        }

        exportCallbacks.clear();
        slotsToSave.clear();
        isScheduled = false;
    }
    
    protected abstract T getSavegame();

    protected void onSavingFailed() { }
    protected void onSavingSucceeded() { }
    
    public Clipboard getSystemAccessClipboard() {
        if (systemAccessClipboard == null) {
            return Gdx.app.getClipboard();
        }
        return systemAccessClipboard;
    }

    protected float getAutoSaveInterval() {
        return autoSaveInterval;
    }

    protected void setAutoSaveInterval(float autoSaveInterval) {
        this.autoSaveInterval = autoSaveInterval;
    }

    public boolean isAutoSaving() {
        return isAutoSaving;
    }

    public void setAutoSaving(boolean isAutoSaving) {
        this.isAutoSaving = isAutoSaving;
    }
    
    public String getSaveSlotName(int slotNumber) {
        ensureSaveSlotNamesAreSupported();
        return saveStore.getString(getNameKey(slotNumber), null);
    }
    
    public void setSaveSlotName(int slotNumber, String name) {
        ensureSaveSlotNamesAreSupported();
        
        String nameKey = getNameKey(slotNumber);
        if (name == null || name.isEmpty()) {
            saveStore.remove(nameKey);
        } else {
            saveStore.putString(nameKey, name);
        }
        saveStore.flush();
    }
    
    public long getSaveTime(int slotNumber) {
        ensureSaveSlotsAreSupported();
        return getSaveTime(getSlotKey(slotNumber));
    }
    
    public long getSaveTime(String slotKey) {
        ensureSaveTimesAreSupported();
        return saveStore.getLong(getTimeKey(slotKey), -1);
    }

    public boolean areSaveSlotsSupported() {
        return saveSlots > 0 && saveSlotPrefix != null && !saveSlotPrefix.isEmpty();
    }

    public boolean areSaveSlotNamesSupported() {
        return areSaveSlotsSupported() && nameSuffix != null && !nameSuffix.isEmpty();
    }
    
    public boolean areSaveTimesSupported() {
        return timeSuffix != null && !timeSuffix.isEmpty();
    }
    
    public int getSaveSlots() {
        return saveSlots;
    }
    
    private String getSlotKey(int slotNumber) {
        return saveSlotPrefix + slotNumber;
    }
    
    private String getNameKey(int slotNumber) {
        return getNameKey(getSlotKey(slotNumber));
    }
    
    private String getNameKey(String slotKey) {
        return slotKey + nameSuffix;
    }
    
    private String getTimeKey(String slotKey) {
        return slotKey + timeSuffix;
    }

    private void ensureSaveSlotsAreSupported() {
        if (!areSaveSlotsSupported()) {
            throw new UnsupportedOperationException("The given config didn't support save slots");
        }
    }

    private void ensureSaveSlotNamesAreSupported() {
        if (!areSaveSlotNamesSupported()) {
            throw new UnsupportedOperationException("The given config didn't support save slot names");
        }
    }

    private void ensureSaveTimesAreSupported() {
        if (!areSaveTimesSupported()) {
            throw new UnsupportedOperationException("The given config didn't support save times");
        }
    }

    // TODO Overhaul Config
    public static class Config extends AbstractConfig {

        public Config(RawConfig rawConfig) {
            super(rawConfig);
        }
        
        public String getSaveStoreName() {
            return getRawConfig().getString("saveStoreName");
        }
        
        public float getAutoSaveInterval() {
            return getRawConfig().getFloat("autoSaveInterval");
        }
        
        public String getAutoSaveSlot() {
            return getRawConfig().getString("autoSaveSlot");
        }
        
        public int getSaveSlots() {
            return getRawConfig().getInt("saveSlots");
        }
        
        public String getSaveSlotPrefix() {
            return getRawConfig().getString("slot");
        }
        
        public String getTimeSuffix() {
            return getRawConfig().getString("timeSuffix");
        }
        
        public String getNameSuffix() {
            return getRawConfig().getString("nameSuffix");
        }
        
    }
    
}
