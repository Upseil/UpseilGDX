package com.upseil.gdx.pool.pair;

import com.badlogic.gdx.utils.Pool;
import com.upseil.gdx.pool.Pooled;
import com.upseil.gdx.util.Pair;

public class PooledPair<A, B> extends Pair<A, B> implements Pooled<PooledPair<A, B>> {

    private Pool<PooledPair<A, B>> pool;

    private boolean freeA = true;
    private boolean freeB = true;

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
        if (freeA && a instanceof Pooled) {
            ((Pooled<?>) a).free();
        }
        if (freeB && b instanceof Pooled) {
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
        setFree(true, true);
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
    
    public void setFree(boolean freeA, boolean freeB) {
        this.freeA = freeA;
        this.freeB = freeB;
    }

    public boolean isFreeA() {
        return freeA;
    }

    public void setFreeA(boolean freeA) {
        this.freeA = freeA;
    }

    public boolean isFreeB() {
        return freeB;
    }

    public void setFreeB(boolean freeB) {
        this.freeB = freeB;
    }
    
}
