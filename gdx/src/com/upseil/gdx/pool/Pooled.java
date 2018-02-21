package com.upseil.gdx.pool;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Pooled<T extends Pooled<T>> extends Poolable {
    
    @JsonIgnore
    Pool<T> getPool();
    
    void setPool(Pool<T> pool);
    
    void free();
    
}
