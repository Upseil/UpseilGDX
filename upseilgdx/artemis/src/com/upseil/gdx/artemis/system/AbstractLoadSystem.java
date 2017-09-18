package com.upseil.gdx.artemis.system;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.upseil.gdx.serialization.Reader;

public abstract class AbstractLoadSystem<T> extends BaseSystem {
    
    private final Reader<T> mapper;
    private final Preferences saveStore;
    private final String autoSaveSlot;
    
    private T savegame;
    private boolean isScheduled;
    
    public AbstractLoadSystem(Reader<T> mapper, String saveStoreName, String autoSaveSlot) {
        this.mapper = mapper;
        saveStore = Gdx.app.getPreferences(saveStoreName);
        this.autoSaveSlot = autoSaveSlot;
    }

    @Override
    protected void initialize() {
        savegame = mapper.read(saveStore.getString(autoSaveSlot, null));
        isScheduled = savegame != null;
    }
    
    public void loadFromAutoSlot() {
        scheduleLoad(autoSaveSlot);
    }
    
    protected void scheduleLoad(String slotName) {
        importGame(saveStore.getString(autoSaveSlot, null));
    }
    
    public void importGame(String data) {
        savegame = mapper.read(data);
        isScheduled = true;
    }
    
    @Override
    protected boolean checkProcessing() {
        return isScheduled;
    }

    @Override
    protected void processSystem() {
        if (savegame == null) {
            onLoadingFailed();
        } else {
            loadGame(savegame);
        }
        
        savegame = null;
        isScheduled = false;
    }

    protected void onLoadingFailed() { }

    protected abstract void loadGame(T savegame);
    
}
