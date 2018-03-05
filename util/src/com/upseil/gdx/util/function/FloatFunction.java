package com.upseil.gdx.util.function;

@FunctionalInterface
public interface FloatFunction<T> {
    
    float apply(T t);
}
