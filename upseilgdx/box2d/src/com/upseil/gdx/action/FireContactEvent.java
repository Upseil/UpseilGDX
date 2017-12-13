package com.upseil.gdx.action;

import com.upseil.gdx.box2d.event.ContactEvent;
import com.upseil.gdx.box2d.event.ContactEventHandler;

public abstract class FireContactEvent<C extends ContactEvent<C>, T extends FireContactEvent<C, T>> extends AbstractAction<C, T> {
    
    @Override
    public boolean act(float deltaTime) {
        getEventHandler().accept(getState());
        return true;
    }

    protected abstract ContactEventHandler<C> getEventHandler();
    
    @Override
    public void free() {
        getState().free();
        super.free();
    }
    
}
