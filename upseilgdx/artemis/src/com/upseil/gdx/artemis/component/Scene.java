package com.upseil.gdx.artemis.component;

import com.artemis.PooledComponent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

public class Scene extends PooledComponent implements Disposable {
    
    private Stage stage;
    
    public Scene initialize(Stage stage) {
        if (this.stage != null) {
            throw new IllegalStateException("Allready initialized");
        }
        if (stage == null) {
            throw new NullPointerException("The given stage mustn't be null");
        }
        this.stage = stage;
        return this;
    }

    public void apply() {
        stage.getViewport().apply();
    }

    public void update(int screenWidth, int screenHeight, boolean centerCamera) {
        stage.getViewport().update(screenWidth, screenHeight, centerCamera);
    }

    public void act(float delta) {
        stage.act(delta);
    }


    public void draw() {
        stage.draw();
    }
    
    @Override
    protected void reset() {
        dispose();
        stage = null;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    
}
