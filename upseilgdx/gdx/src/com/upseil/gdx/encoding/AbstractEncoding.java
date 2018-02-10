package com.upseil.gdx.encoding;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.badlogic.gdx.utils.BufferUtils;

public abstract class AbstractEncoding<T> implements Encoding<T> {
    
    public static final Charset DefaultCharset = Charset.forName("ISO_8859_1");
    
    protected final ByteBuffer buffer;
    private final Charset charset;
    
    protected AbstractEncoding(int bufferSize) {
        this(bufferSize, DefaultCharset);
    }
    
    protected AbstractEncoding(int bufferSize, Charset charset) {
        buffer = BufferUtils.newByteBuffer(bufferSize);
        this.charset = charset;
    }

    @Override
    public String toBase64(T t) {
        return LZString.compressToBase64(new String(toBytes(t), charset));
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
    public T fromBase64(String string) {
        return fromBytes(LZString.decompressFromBase64(string).getBytes(charset));
    }
    
    @Override
    public int size() {
        return buffer.capacity();
    }
    
}
