package com.upseil.gdx.serialization;

import com.upseil.gdx.lzstring.LZString;

public abstract class CompressingSavegameMapper<T> implements SavegameMapper<T> {
    
    @Override
    public String write(T game) {
        try {
            String uncompressedData = writeUncompressed(game);
            return LZString.compressToBase64(uncompressedData);
        } catch (Throwable e) {
            return null;
        }
    }
    
    protected abstract String writeUncompressed(T game) throws Exception;

    @Override
    public T read(String data) {
        try {
            String uncompressedData = LZString.decompressFromBase64(data);
            return readUncompressed(uncompressedData);
        } catch (Throwable e) {
            return null;
        }
    }
    
    protected abstract T readUncompressed(String uncompressedData) throws Exception;
    
}
