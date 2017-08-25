package com.upseil.gdx.scene2d.util;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;

public class SameWidth extends Value {
    
    private float value;
    
    public SameWidth(Layout... actors) {
        value = -1;
        for (Layout layout : actors) {
            float actorWidth = layout.getPrefWidth();
            if (actorWidth > value) {
                value = actorWidth;
            }
        }
    }

    @Override
    public float get(Actor context) {
        return value;
    }
    
}