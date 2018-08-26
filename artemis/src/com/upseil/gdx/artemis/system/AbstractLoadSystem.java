package com.upseil.gdx.artemis.system;

import static com.upseil.gdx.artemis.ArtemisConfigs.SaveConfigValues.*;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.upseil.gdx.artemis.ArtemisConfigs.SaveConfig;
import com.upseil.gdx.serialization.Reader;

public abstract class AbstractLoadSystem<T> extends BaseSystem {
    
    private final Reader<T> mapper;
    
    private String dataToLoad;
    private boolean isScheduled;

    private final Preferences saveStore;
    private final String autoSaveSlot;
    private final int saveSlots;
    private final String saveSlotPrefix;
    
    public AbstractLoadSystem(Reader<T> mapper, SaveConfig config) {
        this.mapper = mapper;
        
        saveStore = Gdx.app.getPreferences(config.get(SaveStoreName));
        autoSaveSlot = config.get(AutoSaveSlot);
        saveSlots = config.getInt(SaveSlots);
        saveSlotPrefix = config.get(SlotPrefix);
    }

    @Override
    protected void initialize() {
    	String data = saveStore.getString(autoSaveSlot);
    	if (data != null && !data.isEmpty()) {
			importGame(data);
		}
    }
    
    public void loadFromAutoSlot() {
        scheduleLoad(autoSaveSlot);
    }
    
    public void loadFromSlot(int slotNumber) {
        ensureSaveSlotsAreSupported();
        scheduleLoad(getSlotKey(slotNumber));
    }
    
    protected void scheduleLoad(String slotName) {
    	importGame(saveStore.getString(slotName));
    }
    
    public void importGame(String data) {
        dataToLoad = data;
        isScheduled = true;
    }
    
    @Override
    protected boolean checkProcessing() {
        return isScheduled;
    }

    @Override
    protected void processSystem() {
    	if (dataToLoad != null && !dataToLoad.isEmpty()) {
            T savegame = mapper.read(dataToLoad);
            if (savegame == null) {
                onLoadingFailed();
            } else {
                loadGame(savegame);
            } 
        }
        dataToLoad = null;
        isScheduled = false;
    }

    protected void onLoadingFailed() { }

    protected abstract void loadGame(T savegame);

    public boolean areSaveSlotsSupported() {
        return saveSlots > 0 && saveSlotPrefix != null && !saveSlotPrefix.isEmpty();
    }
    
    public int getSaveSlots() {
        return saveSlots;
    }
    
    private String getSlotKey(int slotNumber) {
        return saveSlotPrefix + slotNumber;
    }

    private void ensureSaveSlotsAreSupported() {
        if (!areSaveSlotsSupported()) {
            throw new UnsupportedOperationException("The given config didn't support save slots");
        }
    }
    
}
