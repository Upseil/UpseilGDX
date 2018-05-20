package com.upseil.gdx.scene2d;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SimpleChangeListener extends ChangeListener {
    
    private final Runnable action;
    
    public SimpleChangeListener(Runnable action) {
        this.action = action;
    }

    @Override
    public void changed(ChangeEvent event, Actor actor) {
        action.run();
    }
    
}
