package com.upseil.gdx.scene2d;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public abstract class KeyInputListener implements EventListener {
    
    @Override
    public boolean handle(Event e) {
        if (!(e instanceof InputEvent)) return false;
        InputEvent event = (InputEvent) e;

        switch (event.getType()) {
        case keyDown:
            return keyDown(event, event.getKeyCode());
        case keyUp:
            return keyUp(event, event.getKeyCode());
        case keyTyped:
            return keyTyped(event, event.getCharacter());
        default:
            return false;
        }
    }

    /** Called when a key goes down. When true is returned, the event is {@link Event#handle() handled}. */
    public abstract boolean keyDown (InputEvent event, int keyCode);

    /** Called when a key goes up. When true is returned, the event is {@link Event#handle() handled}. */
    public abstract boolean keyUp (InputEvent event, int keyCode);

    /** Called when a key is typed. When true is returned, the event is {@link Event#handle() handled}.
     * @param character May be 0 for key typed events that don't map to a character (ctrl, shift, etc). */
    public abstract boolean keyTyped (InputEvent event, char character);
    
}
