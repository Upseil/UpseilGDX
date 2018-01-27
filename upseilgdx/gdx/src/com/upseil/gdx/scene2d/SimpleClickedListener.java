package com.upseil.gdx.scene2d;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class SimpleClickedListener extends ClickListener {
    
    private final Runnable action;
    
    public SimpleClickedListener(Runnable action) {
        this.action = action;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        action.run();
    }
    
}
