package com.upseil.gdx.encoding;

public interface Encoder<T> {
    
    public void toBytes(T t, byte[] target);
    public String toBase64(T t);
    
    public int size();
    
    public default byte[] toBytes(T t) {
        byte[] bytes = new byte[size()];
        toBytes(t, bytes);
        return bytes;
    }
    
}
