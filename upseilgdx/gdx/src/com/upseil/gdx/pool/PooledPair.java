package com.upseil.gdx.pool;

import com.badlogic.gdx.utils.Pool;
import com.upseil.gdx.util.Pair;

public class PooledPair<A, B> extends Pair<A, B> implements Pooled<PooledPair<A, B>> {

    private Pool<PooledPair<A, B>> pool;

    @Override
    public Pool<PooledPair<A, B>> getPool() {
        return pool;
    }

    @Override
    public void setPool(Pool<PooledPair<A, B>> pool) {
        this.pool = pool;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void free() {
        if (a instanceof Pooled) {
            ((Pooled<?>) a).free();
        }
        if (b instanceof Pooled) {
            ((Pooled<?>) b).free();
        }
        if (pool != null) {
            ((Pool) pool).free(this);
        }
    }
    
    @Override
    public void reset() {
        pool = null;
        set(null, null);
    }

    @Override
    public PooledPair<A, B> set(A a, B b) {
        super.set(a, b);
        return this;
    }

    @Override
    public PooledPair<A, B> setA(A a) {
        super.setA(a);
        return this;
    }

    @Override
    public PooledPair<A, B> setB(B b) {
        super.setB(b);
        return this;
    }
    
}
