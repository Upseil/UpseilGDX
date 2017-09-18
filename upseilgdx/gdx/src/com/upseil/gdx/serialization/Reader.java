package com.upseil.gdx.serialization;

public interface Reader<T> {
    
    T read(String data);
    
}