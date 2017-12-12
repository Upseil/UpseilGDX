package com.upseil.gdx.pool;

import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;

public class PooledPools {
    
    private static final ObjectMap<Class<?>, PooledPool<?>> pools = new ObjectMap<>();
    
    public static <T extends Pooled<T>> Pool<T> get(Class<T> type) {
        return get(type, 100);
    }
    
    @SuppressWarnings("unchecked")
    public static <T extends Pooled<T>> Pool<T> get(Class<T> type, int max) {
        PooledPool<T> pool = (PooledPool<T>) pools.get(type);
        if (pool == null) {
            pool = new ReflectionPooledPool<>(type, 4, max);
            set(type, pool);
        }
        return pool;
    }

    public static <T extends Pooled<T>> void set(Class<T> type, PooledPool<T> pool) {
        pools.put(type, pool);
    }
    
    public static <T extends Pooled<T>> T obtain(Class<T> type) {
        return get(type).obtain();
    }

    private PooledPools() { }
    
}
