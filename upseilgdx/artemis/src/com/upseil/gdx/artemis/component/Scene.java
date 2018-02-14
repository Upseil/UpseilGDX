package com.upseil.gdx.artemis.component;

import com.artemis.PooledComponent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

public class Scene extends PooledComponent implements Disposable {
    
    private Stage stage;
    private boolean manualAct;
    private boolean paused;

    public Scene initialize(Stage stage) {
        return initialize(stage, false);
    }
    
    public Scene initialize(Stage stage, boolean manualAct) {
        if (this.stage != null) {
            throw new IllegalStateException("Allready initialized");
        }
        if (stage == null) {
            throw new NullPointerException("The given stage mustn't be null");
        }
        this.stage = stage;
        this.manualAct = manualAct;
        return this;
    }
    
    public Stage getStage() {
        return stage;
    }

    public void apply() {
        stage.getViewport().apply();
    }

    public void update(int screenWidth, int screenHeight, boolean centerCamera) {
        stage.getViewport().update(screenWidth, screenHeight, centerCamera);
    }

    public void act(float delta) {
        if (!manualAct) {
            stage.act(paused ? 0 : delta);
        }
    }

    public void draw() {
        stage.draw();
    }
    
    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    @Override
    protected void reset() {
        dispose();
        stage = null;
        manualAct = false;
        paused = false;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    
}
