package com.upseil.gdx.artemis.system;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.upseil.gdx.scene2d.SimpleKeyInputListener;
import com.upseil.gdx.util.function.BooleanFunction;

public class SimpleKeyTypedListener extends SimpleKeyInputListener {
    
    private final BooleanFunction<InputEvent> handler;
    
    public SimpleKeyTypedListener(BooleanFunction<InputEvent> handler) {
        this.handler = handler;
    }

    @Override
    public boolean keyTyped(InputEvent event, char character) {
        return handler.apply(event);
    }
    
}
