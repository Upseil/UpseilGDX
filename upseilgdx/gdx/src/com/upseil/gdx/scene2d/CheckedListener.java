package com.upseil.gdx.scene2d;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public abstract class CheckedListener extends ChangeListener {
    
    @Override
    public void changed(ChangeEvent event, Actor actor) {
        if (!(actor instanceof Button)) {
            throw new IllegalArgumentException(CheckedListener.class.getSimpleName() + " not compatible with " + actor.getClass().getSimpleName());
        }
        
        checkedChanged(((Button) actor).isChecked());
    }

    protected abstract void checkedChanged(boolean checked);
    
}
