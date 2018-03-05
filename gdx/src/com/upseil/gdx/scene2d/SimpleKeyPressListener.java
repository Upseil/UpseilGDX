package com.upseil.gdx.scene2d;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.upseil.gdx.util.function.BooleanFunction;

public class SimpleKeyPressListener extends SimpleKeyInputListener {
    
    private final BooleanFunction<InputEvent> keyDownHandler;
    private final BooleanFunction<InputEvent> keyUpHandler;

    public SimpleKeyPressListener(BooleanFunction<InputEvent> keyDownHandler, BooleanFunction<InputEvent> keyUpHandler) {
        this.keyDownHandler = keyDownHandler;
        this.keyUpHandler = keyUpHandler;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        return keyDownHandler.apply(event);
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        return keyUpHandler.apply(event);
    }
    
}
