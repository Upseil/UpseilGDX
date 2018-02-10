package com.upseil.gdx.encoding;

public interface Decoder<T> {
    
    public T fromBytes(byte[] source);
    public T fromBase64(String string);
    
    public int size();
    
}
