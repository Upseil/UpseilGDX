package com.upseil.gdx.action;

import com.upseil.gdx.pool.Pooled;
import com.upseil.gdx.util.function.FloatBooleanFunction;

public interface Action<S, T extends Action<S, T>> extends Pooled<T> {
    
    boolean act(float deltaTime);
    
    void restart();
    
    S getState();
    void setState(S state);
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <S, T extends Action<S, T>> Action<S, T> Unsafe(FloatBooleanFunction action) {
        return new AbstractAction() {
            @Override
            public boolean act(float deltaTime) {
                return action.apply(deltaTime);
            }
        };
    }
    
}
