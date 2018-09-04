package com.upseil.gdx.artemis.system;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.upseil.gdx.action.Action;
import com.upseil.gdx.artemis.component.Ignore;
import com.upseil.gdx.artemis.component.Screen;

public class ScreenManager extends BaseSystem {
    
    private ComponentMapper<Screen> screenMapper;
    private ComponentMapper<Ignore> ignoreMapper;
    
    private int currentScreenId = -1;
    
    private int newScreenId = -1 ;
    private boolean executeActions;
    
    private boolean exitActionInProgress;
    private boolean entranceActionInProgress;
    
    @Override
    protected boolean checkProcessing() {
        return newScreenId != -1;
    }
    
    @Override
    protected void processSystem() {
        if (currentScreenId != -1) {
            Screen currentScreen = screenMapper.get(currentScreenId);
            boolean executeExitAction = executeActions && currentScreen.getExitAction() != null;
            if (!exitActionInProgress && executeExitAction) {
                Action<Screen, ?> exitAction = currentScreen.getExitAction();
                exitAction.restart();
                exitActionInProgress = true;
            }
            if (!executeExitAction ||(exitActionInProgress && currentScreen.getExitAction().act(world.delta))) {
                hideScenes(currentScreen);
                currentScreenId = -1;
                exitActionInProgress = false;
            }
        }
        
        if (currentScreenId == -1) {
            Screen newScreen = screenMapper.get(newScreenId);
            boolean executeEntranceAction = executeActions && newScreen.getEntranceAction() != null;
            if (!entranceActionInProgress && executeEntranceAction) {
                Action<Screen, ?> entranceAction = newScreen.getEntranceAction();
                entranceAction.restart();
                entranceActionInProgress = true;
            }
            if (!executeEntranceAction || (entranceActionInProgress && newScreen.getEntranceAction().act(world.delta))) {
                showScenes(newScreen);
                currentScreenId = newScreenId;
                newScreenId = -1;
                entranceActionInProgress = false;
            }
        }
    }
    
    private void hideScenes(Screen screen) {
        for (int index = 0; index < screen.size(); index++) {
            ignoreMapper.create(screen.getSceneId(index));
        }
    }
    
    private void showScenes(Screen screen) {
        for (int index = 0; index < screen.size(); index++) {
            ignoreMapper.remove(screen.getSceneId(index));
        }
    }

    public void setScreen(int entityId) {
        setScreen(entityId, true);
    }

    public void setScreen(int entityId, boolean executeActions) {
        if (currentScreenId != entityId) {
            this.newScreenId = entityId;
            this.executeActions = executeActions;
        }
    }
    
}
