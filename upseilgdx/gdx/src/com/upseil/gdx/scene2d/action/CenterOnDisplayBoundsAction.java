package com.upseil.gdx.scene2d.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;

public class CenterOnDisplayBoundsAction extends Action {

    @Override
    public boolean act(float delta) {
        float displayWidth = Gdx.graphics.getWidth();
        float displayHeight = Gdx.graphics.getHeight();
        float x = displayWidth / 2 - actor.getWidth() / 2;
        float y = displayHeight / 2 - actor.getHeight() / 2;
        actor.setPosition(Math.round(x), Math.round(y));
        return false;
    }
    
}
