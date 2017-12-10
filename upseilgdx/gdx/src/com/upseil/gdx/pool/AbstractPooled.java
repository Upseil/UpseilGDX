package com.upseil.gdx.pool;

import com.badlogic.gdx.utils.Pool;

public abstract class AbstractPooled implements Pooled {

    @SuppressWarnings("rawtypes")
    private Pool pool;

    @SuppressWarnings("rawtypes")
    @Override
    public Pool getPool() {
        return pool;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void setPool(Pool pool) {
        this.pool = pool;
    }
    
    @SuppressWarnings("unchecked")
    public void free() {
        if (pool != null) {
            pool.free(this);
        }
    }
    
    @Override
    public void reset() {
        pool = null;
    }
    
}
