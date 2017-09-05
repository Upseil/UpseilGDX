package com.upseil.gdx.pool;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.Pools;

public interface Pooled extends Poolable {
    
    @SuppressWarnings("rawtypes")
    Pool getPool();
    @SuppressWarnings("rawtypes")
    void setPool(Pool pool);
    
    public static <T extends Pooled> T get(Class<T> type) {
        Pool<T> pool = Pools.get(type);
        T pooled = pool.obtain();
        pooled.setPool(pool);
        return pooled;
    }
    
}
