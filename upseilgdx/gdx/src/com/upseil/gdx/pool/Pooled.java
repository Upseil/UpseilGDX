package com.upseil.gdx.pool;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

public interface Pooled<T extends Pooled<T>> extends Poolable {
    
    Pool<T> getPool();
    
    void setPool(Pool<T> pool);
    
    void free();
    
}
