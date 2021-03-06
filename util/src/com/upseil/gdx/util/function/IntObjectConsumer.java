package com.upseil.gdx.util.function;

@FunctionalInterface
public interface IntObjectConsumer<T> {
    
    void accept(int i, T t);
    
}
