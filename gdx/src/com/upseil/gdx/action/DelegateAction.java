package com.upseil.gdx.action;

import com.badlogic.gdx.utils.Pool;

public abstract class DelegateAction<S, A extends Action<S, A>, T extends DelegateAction<S, A, T>> extends AbstractAction<S, T> {
    
    private A action;
    
    @Override
    public boolean act(float deltaTime) {
        Pool<T> pool = getPool();
        setPool(null);
        try {
            return delegate(deltaTime);
        } finally {
            setPool(pool);
        }
    }
    
    protected abstract boolean delegate(float deltaTime);

    public A getAction() {
        return action;
    }

    public void setAction(A action) {
        this.action = action;
    }

    @Override
    public void setState(S state) {
        if (action != null) action.setState(state);
        super.setState(state);
    }

    @Override
    public void restart() {
        if (getState() != null) action.restart();
    }
    
    @Override
    public void free() {
        action.free();
        super.free();
    }
    
    @Override
    public void reset() {
        super.reset();
        action = null;
    }
    
}
