package com.upseil.gdx.pool.pair;

import com.upseil.gdx.pool.PooledPool;

public class PairPool<A, B> extends PooledPool<PooledPair<A, B>> {
    
    public PairPool() {
        super();
    }

    public PairPool(int initialCapacity, int max) {
        super(initialCapacity, max);
    }

    public PairPool(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    protected PooledPair<A, B> newObject() {
        return new PooledPair<>();
    }
    
}
