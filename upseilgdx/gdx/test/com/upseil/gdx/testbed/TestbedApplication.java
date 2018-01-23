package com.upseil.gdx.testbed;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;

public abstract class TestbedApplication extends InputAdapter implements ApplicationListener {
    
    protected static LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
    
    protected void clearScreen() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
    
}
