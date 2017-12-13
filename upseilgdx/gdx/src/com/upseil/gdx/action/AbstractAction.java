package com.upseil.gdx.action;

import com.upseil.gdx.pool.AbstractPooled;

public abstract class AbstractAction<S, T extends AbstractAction<S, T>> extends AbstractPooled<T> implements Action<S, T> {
    
    private S state;

    public S getState() {
        return state;
    }

    public void setState(S state) {
        this.state = state;
    }
    
    @Override
    public void restart() {
    }
    
    @Override
    public void reset() {
        super.reset();
        restart();
        state = null;
    }

}
