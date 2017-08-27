package com.upseil.gdx.serialization;

public interface SavegameMapper<T> {
    
    String write(T savegame);
    T read(String data);
    
}
