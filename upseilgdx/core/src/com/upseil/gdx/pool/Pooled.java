package com.upseil.gdx.pool;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

public interface Pooled extends Poolable {
    
    @SuppressWarnings("rawtypes")
    Pool getPool();
    @SuppressWarnings("rawtypes")
    void setPool(Pool pool);
    
}
