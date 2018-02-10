package com.upseil.gdx.encoding;

public interface Encoder<T> {
    
    public void toBytes(T t, byte[] target);
    
    public int size();

    public default String toBase64(T t) {
        return Base64.toBase64(toBytes(t));
    }
    
    public default byte[] toBytes(T t) {
        byte[] bytes = new byte[size()];
        toBytes(t, bytes);
        return bytes;
    }
    
}
