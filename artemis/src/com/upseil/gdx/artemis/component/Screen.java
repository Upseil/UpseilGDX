package com.upseil.gdx.artemis.component;

import com.artemis.PooledComponent;
import com.artemis.utils.IntBag;
import com.upseil.gdx.action.Action;

public class Screen extends PooledComponent {
    
    private final IntBag sceneIds;
    
    private Action<Screen, ?> entranceAction;
    private Action<Screen, ?> exitAction;
    
    public Screen() {
        sceneIds = new IntBag(4);
    }
    
    public void addScene(int sceneId) {
        sceneIds.add(sceneId);
    }

    public boolean removeScene(int sceneId) {
        return sceneIds.removeValue(sceneId);
    }
    
    public int removeIndex(int index) {
        return sceneIds.remove(index);
    }
    
    public int getSceneId(int index) {
        return sceneIds.get(index);
    }
    
    public IntBag getSceneIds() {
        return sceneIds;
    }
    
    public int size() {
        return sceneIds.size();
    }

    public Action<Screen, ?> getEntranceAction() {
        return entranceAction;
    }

    public void setEntranceAction(Action<Screen, ?> entranceAction) {
        this.entranceAction = entranceAction;
        this.entranceAction.setState(this);
    }

    public Action<Screen, ?> getExitAction() {
        return exitAction;
    }

    public void setExitAction(Action<Screen, ?> exitAction) {
        this.exitAction = exitAction;
        this.exitAction.setState(this);
    }

    @Override
    protected void reset() {
        sceneIds.clear();
        entranceAction.free();
        entranceAction = null;
        exitAction.free();
        exitAction = null;
    }
    
}
