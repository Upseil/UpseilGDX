package com.upseil.gdx.artemis.system;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.upseil.gdx.serialization.Reader;

public abstract class AbstractLoadSystem<T> extends BaseSystem {
    
    private final Reader<T> mapper;
    private final Preferences saveStore;
    private final String autoSaveSlot;
    
    private String dataToLoad;
    private boolean isScheduled;
    
    public AbstractLoadSystem(Reader<T> mapper, String saveStoreName, String autoSaveSlot) {
        this.mapper = mapper;
        saveStore = Gdx.app.getPreferences(saveStoreName);
        this.autoSaveSlot = autoSaveSlot;
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
    	T savegame = mapper.read(dataToLoad);
        if (savegame == null) {
            onLoadingFailed();
        } else {
            loadGame(savegame);
        }
        
        dataToLoad = null;
        isScheduled = false;
    }

    protected void onLoadingFailed() { }

    protected abstract void loadGame(T savegame);
    
}
