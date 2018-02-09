package com.upseil.gdx.serialization;

import com.badlogic.gdx.Gdx;
import com.upseil.gdx.lzstring.LZString;

public abstract class CompressingMapper<T> implements Mapper<T> {
    
    private boolean compressing = true;
    
    @Override
    public String write(T object) {
        try {
            String uncompressedData = writeUncompressed(object);
            return compressing ? LZString.compressToBase64(uncompressedData) : uncompressedData;
        } catch (Throwable e) {
            Gdx.app.error("Serialization", "Error writing the object", e);
            return null;
        }
    }
    
    protected abstract String writeUncompressed(T game) throws Exception;

    @Override
    public T read(String data) {
        try {
            String uncompressedData = compressing ? LZString.decompressFromBase64(data) : data;
            return readUncompressed(uncompressedData);
        } catch (Throwable e) {
            Gdx.app.error("Serialization", "Error reading the data", e);
            return null;
        }
    }
    
    protected abstract T readUncompressed(String uncompressedData) throws Exception;

    public boolean isCompressing() {
        return compressing;
    }

    public void setCompressing(boolean compressing) {
        this.compressing = compressing;
    }
    
}
