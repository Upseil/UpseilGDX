package com.upseil.gdx.util.function;

@FunctionalInterface
public interface BooleanFunction<T> {
    
    boolean apply(T t);
    
}
