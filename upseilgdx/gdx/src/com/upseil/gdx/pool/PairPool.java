package com.upseil.gdx.pool;

import com.badlogic.gdx.utils.Pool;
import com.upseil.gdx.util.Pair;

public class PairPool<A, B> extends Pool<Pair<A, B>> {
    
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
    protected Pair<A, B> newObject() {
        return new Pair<>();
    }
    
    @Override
    protected void reset(Pair<A, B> pair) {
        pair.set(null, null);
    }
    
}
