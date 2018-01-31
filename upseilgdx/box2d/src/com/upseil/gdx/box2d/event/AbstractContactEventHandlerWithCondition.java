package com.upseil.gdx.box2d.event;

public abstract class AbstractContactEventHandlerWithCondition<E extends ContactEvent<E>> extends AbstractContactEventHandler<E> {
    
    @Override
    public void accept(E event) {
        if (accepts(event)) {
            handle(event);
        }
    }
    
    protected abstract boolean accepts(E event);
    
}
