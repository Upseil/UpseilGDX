package com.upseil.gdx.artemis.system;

import java.util.function.Consumer;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Clipboard;
import com.badlogic.gdx.utils.ObjectSet;
import com.upseil.gdx.artemis.ArtemisApplicationAdapter;
import com.upseil.gdx.serialization.Writer;

public abstract class AbstractSaveSystem<T> extends BaseSystem {
    
    private final Writer<T> mapper;
    private final Clipboard systemAccessClipboard;
    private final Preferences saveStore;
    private final String autoSaveSlot;
    
    private final Array<Consumer<String>> exportCallbacks;
    private final ObjectSet<String> slotsToSave;

    private float autoSaveInterval;
    private float accumulatedDelta;
    private boolean isAutoSaving;
    private boolean isScheduled;
    
    public AbstractSaveSystem(Writer<T> mapper, Clipboard systemAccessClipboard, String saveStoreName, String autoSaveSlot) {
        this.mapper = mapper;
        this.systemAccessClipboard = systemAccessClipboard;
        this.saveStore = Gdx.app.getPreferences(saveStoreName);
        this.autoSaveSlot = autoSaveSlot;
        
        exportCallbacks = new Array<>(4);
        slotsToSave = new ObjectSet<>();
        autoSaveInterval = -1;
    }
    
    public void saveToAutoSlot() {
        scheduleSave(autoSaveSlot);
        accumulatedDelta = 0;
    }
    
    protected void scheduleSave(String slotName) {
        isScheduled = true;
        slotsToSave.add(slotName);
    }
    
    public void exportSave(Consumer<String> callback) {
        exportCallbacks.add(callback);
        isScheduled = true;
    }

    public void deleteSave(String slotName) {
        saveStore.remove(slotName);
        saveStore.flush();
    }

    public void reset(boolean resetAll) {
        if (resetAll) {
            saveStore.clear();
        } else {
            saveStore.remove(autoSaveSlot);
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
                for (String slot : slotsToSave) {
                    saveStore.putString(slot, data);
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
    
}
