package com.upseil.gdx.pool;

import com.badlogic.gdx.utils.Pool;

public abstract class PooledPool<T extends Pooled<T>> extends Pool<T> {
    
    public PooledPool() {
        super();
    }
    
    public PooledPool(int initialCapacity) {
        super(initialCapacity);
    }

    public PooledPool(int initialCapacity, int max) {
        super(initialCapacity, max);
    }

    @Override
    public T obtain() {
        T object = super.obtain();
        object.setPool(this);
        return object;
    }
    
    @Override
    protected void reset(T object) {
        object.reset();
    }
    
}
