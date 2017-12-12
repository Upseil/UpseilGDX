package com.upseil.gdx.action;

public abstract class ContextualAction<C, T extends ContextualAction<C, T>> extends AbstractAction<T> {
    
    private C context;

    public C getContext() {
        return context;
    }

    public void setContext(C context) {
        this.context = context;
    }
    
    @Override
    public void reset() {
        super.reset();
        context = null;
    }
    
}
