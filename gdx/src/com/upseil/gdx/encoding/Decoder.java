package com.upseil.gdx.encoding;

public interface Decoder<T> {
    
    public T fromBytes(byte[] source);
    
    public int size();

    public default T fromBase64(String data) {
        return fromBytes(Base64.fromBase64(data));
    }
    
}
