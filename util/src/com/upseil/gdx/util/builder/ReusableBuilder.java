package com.upseil.gdx.util.builder;

public interface ReusableBuilder<T> extends Builder<T> {
    
    public ReusableBuilder<T> reset();
    
}
