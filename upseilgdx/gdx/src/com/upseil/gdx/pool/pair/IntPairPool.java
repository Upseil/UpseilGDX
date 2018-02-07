package com.upseil.gdx.pool.pair;

import com.upseil.gdx.pool.PooledPool;

public class IntPairPool<T> extends PooledPool<PooledIntPair<T>> {
    
    public IntPairPool() {
        super();
    }

    public IntPairPool(int initialCapacity, int max) {
        super(initialCapacity, max);
    }

    public IntPairPool(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    protected PooledIntPair<T> newObject() {
        return new PooledIntPair<>();
    }
    
}
