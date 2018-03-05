package com.upseil.gdx.scene2d;

import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class SimpleKeyInputListener extends KeyInputListener {
    
    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        return false;
    }
    
    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        return false;
    }
    
    @Override
    public boolean keyTyped(InputEvent event, char character) {
        return false;
    }
    
}
