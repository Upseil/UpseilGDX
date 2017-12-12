package com.upseil.gdx.pool;

import com.badlogic.gdx.utils.Pool;

public abstract class AbstractPooled<T extends AbstractPooled<T>> implements Pooled<T> {

    private Pool<T> pool;

    @Override
    public Pool<T> getPool() {
        return pool;
    }

    @Override
    public void setPool(Pool<T> pool) {
        this.pool = pool;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void free() {
        if (pool != null) {
            ((Pool) pool).free(this);
        }
    }
    
    @Override
    public void reset() {
        pool = null;
    }
    
}
