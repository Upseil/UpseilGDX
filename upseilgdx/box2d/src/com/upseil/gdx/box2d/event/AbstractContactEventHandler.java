package com.upseil.gdx.box2d.event;

public abstract class AbstractContactEventHandler<E extends ContactEvent<E>> implements ContactEventHandler<E> {
    
    @Override
    public void accept(E event) {
        handle(event);
    }

    protected abstract void handle(E event);
    
}
