package com.upseil.gdx.pool.pair;

import com.badlogic.gdx.utils.Pool;
import com.upseil.gdx.pool.Pooled;
import com.upseil.gdx.util.IntPair;

public class PooledIntPair<T> extends IntPair<T> implements Pooled<PooledIntPair<T>> {

    private Pool<PooledIntPair<T>> pool;

    @Override
    public Pool<PooledIntPair<T>> getPool() {
        return pool;
    }

    @Override
    public void setPool(Pool<PooledIntPair<T>> pool) {
        this.pool = pool;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void free() {
        a = 0;
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
        set(0, null);
    }

    @Override
    public PooledIntPair<T> set(int a, T b) {
        super.set(a, b);
        return this;
    }

    @Override
    public PooledIntPair<T> setA(int a) {
        super.setA(a);
        return this;
    }

    @Override
    public PooledIntPair<T> setB(T b) {
        super.setB(b);
        return this;
    }
    
}
