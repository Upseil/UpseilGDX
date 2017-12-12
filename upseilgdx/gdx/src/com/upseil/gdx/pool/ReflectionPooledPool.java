package com.upseil.gdx.pool;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class ReflectionPooledPool<T extends Pooled<T>> extends PooledPool<T> {
    
    private final Constructor constructor;
    
    public ReflectionPooledPool(Class<T> type) {
        this(type, 16, Integer.MAX_VALUE);
    }
    
    public ReflectionPooledPool(Class<T> type, int initialCapacity) {
        this(type, initialCapacity, Integer.MAX_VALUE);
    }
    
    public ReflectionPooledPool(Class<T> type, int initialCapacity, int max) {
        super(initialCapacity, max);
        constructor = findConstructor(type);
    }
    
    private Constructor findConstructor(Class<T> type) {
        try {
            return ClassReflection.getConstructor(type, (Class[]) null);
        } catch (Exception ex1) {
            try {
                Constructor constructor = ClassReflection.getDeclaredConstructor(type, (Class[]) null);
                constructor.setAccessible(true);
                return constructor;
            } catch (ReflectionException ex2) {
                throw new IllegalArgumentException("Can't find no-arg constructor: " + type.getName(), ex2);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    protected T newObject() {
        try {
            return (T) constructor.newInstance((Object[]) null);
        } catch (Exception ex) {
            throw new GdxRuntimeException("Unable to create new instance: " + constructor.getDeclaringClass().getName(), ex);
        }
    }
    
}
