package com.upseil.gdx.action;

import com.upseil.gdx.pool.Pooled;

public interface Action<T extends Action<T>> extends Pooled<T> {
    
    boolean act(float deltaTime);
    
}
