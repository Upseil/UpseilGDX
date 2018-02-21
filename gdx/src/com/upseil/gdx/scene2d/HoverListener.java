package com.upseil.gdx.scene2d;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;

public abstract class HoverListener implements EventListener {
    
    private boolean isOver;
    
    @Override
    public boolean handle(Event e) {
        if (!(e instanceof InputEvent)) return false;
        InputEvent event = (InputEvent) e;
        
        Type type = event.getType();
        if ((type == Type.enter || type == Type.exit) && event.getPointer() == -1) {
            isOver = type == Type.enter;
            if (type == Type.enter) onEnter();
            if (type == Type.exit) onExit();
        }
        return false;
    }

    public abstract void onEnter();
    public abstract void onExit();
    
    public boolean isOver() {
        return isOver;
    }
    
}
