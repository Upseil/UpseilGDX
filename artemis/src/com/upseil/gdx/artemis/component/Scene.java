package com.upseil.gdx.artemis.component;

import com.artemis.PooledComponent;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Scene extends PooledComponent implements Disposable {
    
    private Stage stage;
    
    private boolean manualAct;
    private boolean centerCamera;
    private float timeScale;
    private float activeTimeScale;
    
    public Scene() {
        manualAct = false;
        centerCamera = true;
        timeScale = 1;
        activeTimeScale = 1;
    }

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
    
    public Viewport getViewport() {
        return stage.getViewport();
    }

    public void apply() {
        stage.getViewport().apply();
    }

    public void update(int screenWidth, int screenHeight) {
        stage.getViewport().update(screenWidth, screenHeight, centerCamera);
    }

    public void act(float delta) {
        if (!manualAct) {
            stage.act(activeTimeScale * delta);
        }
    }

    public void draw() {
        stage.draw();
    }

    public boolean isCenterCamera() {
        return centerCamera;
    }

    public void setCenterCamera(boolean centerCamera) {
        this.centerCamera = centerCamera;
    }

    public boolean isPaused() {
        return activeTimeScale == 0;
    }

    public void setPaused(boolean paused) {
        activeTimeScale = paused ? 0 : timeScale;
    }
    
    public void setTimeScale(float timeScale) {
        this.timeScale = timeScale;
        activeTimeScale = timeScale;
    }

    public float getTimeScale() {
        return timeScale;
    }

    public float getActiveTimeScale() {
        return activeTimeScale;
    }
    
    public void clear() {
        stage.clear();
    }
    
    public void addActor(Actor actor) {
        stage.addActor(actor);
    }
    
    public float getWidth() {
        return stage.getWidth();
    }
    
    public float getHeight() {
        return stage.getHeight();
    }

    @Override
    protected void reset() {
        dispose();
        stage = null;
        manualAct = false;
        centerCamera = true;
        timeScale = 1;
        activeTimeScale = 1;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    
}
