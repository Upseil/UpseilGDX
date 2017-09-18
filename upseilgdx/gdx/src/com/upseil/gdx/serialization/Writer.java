package com.upseil.gdx.serialization;

public interface Writer<T> {
    
    String write(T object);
    
}