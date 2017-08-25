package com.upseil.gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap.Entry;

public abstract class AbstractApplicationAdapter extends ApplicationAdapter {
    
    @Override
    public void render() {
        render(Gdx.graphics.getDeltaTime());
    }

    protected abstract void render(float deltaTime);
    
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
