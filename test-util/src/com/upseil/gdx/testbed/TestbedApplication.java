package com.upseil.gdx.testbed;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap.Entry;

public abstract class TestbedApplication extends InputAdapter implements ApplicationListener {
    
    protected static LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
    
    protected static void launch(ApplicationListener listener) {
        launch(listener, configuration);
    }

    protected static void launch(ApplicationListener listener, LwjglApplicationConfiguration configuration) {
        if (configuration.title == null) configuration.title = listener.getClass().getSimpleName();
        new LwjglApplication(listener, configuration);
    }
    
    protected float deltaTimeLimit = 0.5f;
    
    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        if (deltaTimeLimit > 0) {
            render(Math.min(deltaTimeLimit, deltaTime));
        } else {
            render(deltaTime);
        }
    }

    protected abstract void render(float deltaTime);

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
    
    protected Skin loadSkin(String path) {
        return loadSkin(path, true, true);
    }

    protected Skin loadSkin(String path, boolean registerColors, boolean enableFontMarkup) {
        Skin skin = new Skin(Gdx.files.internal(path));
        if (registerColors) {
            for (Entry<String, Color> colorEntry : skin.getAll(Color.class).entries()) {
                Colors.put(colorEntry.key, colorEntry.value);
            } 
        }
        if (enableFontMarkup) {
            for (Entry<String, BitmapFont> fontEntry : skin.getAll(BitmapFont.class)) {
                fontEntry.value.getData().markupEnabled = true;
            }
        }
        return skin;
    }
    
}
