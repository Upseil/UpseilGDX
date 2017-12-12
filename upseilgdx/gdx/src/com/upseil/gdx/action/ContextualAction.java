package com.upseil.gdx.action;

import com.upseil.gdx.util.function.FloatBooleanFunction;

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
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <C, T extends ContextualAction<C, T>> ContextualAction<C, T> Unsafe(FloatBooleanFunction action) {
        return new ContextualAction() {
            @Override
            public boolean act(float deltaTime) {
                return action.apply(deltaTime);
            }
        };
    }
    
}
