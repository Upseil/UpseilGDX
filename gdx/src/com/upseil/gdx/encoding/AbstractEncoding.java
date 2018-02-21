package com.upseil.gdx.encoding;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.badlogic.gdx.utils.BufferUtils;

public abstract class AbstractEncoding<T> implements Encoding<T> {
    
    public static final Charset DefaultCharset = Charset.forName("UTF-8");
    
    protected final ByteBuffer buffer;
    
    protected AbstractEncoding(int bufferSize) {
        buffer = BufferUtils.newByteBuffer(bufferSize);
    }
    
    @Override
    public void toBytes(T t, byte[] target) {
        if (target.length != size()) {
            throw new IllegalArgumentException("Invalid capacity of the target byte array: " + target.length + " != " + size());
        }
        
        buffer.clear();
        writeToBuffer(t);
        buffer.rewind();
        buffer.get(target);
    }
    
    protected abstract void writeToBuffer(T t);
    
    @Override
    public T fromBytes(byte[] source) {
        if (source.length != size()) {
            throw new IllegalArgumentException("Invalid capacity of the source byte array: " + source.length + " != " + size());
        }
        
        buffer.clear();
        buffer.put(source);
        buffer.rewind();
        return readFromBuffer();
    }
    
    protected abstract T readFromBuffer();
    
    @Override
    public int size() {
        return buffer.capacity();
    }
    
}
