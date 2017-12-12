package com.upseil.gdx.box2d.action;

import com.upseil.gdx.pool.Pooled;

public interface PhysicsAction<T extends PhysicsAction<T>> extends Pooled<T> {
    
    boolean act(float deltaTime);
    
}
