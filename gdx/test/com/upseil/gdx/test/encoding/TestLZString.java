package com.upseil.gdx.test.encoding;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

import org.junit.Test;

import com.upseil.gdx.encoding.LZString;

public class TestLZString {
    
    private static final String decompressed = "Hello LZString!";
    private static final String   compressed = "BIUwNmD2AEAyBaBlALgJwJYDsDmBCIA=";
    
    @Test
    public void testCompressToBase64() {
        assertThat(LZString.compressToBase64(decompressed), is(compressed));
    }
    
    @Test
    public void testDecompressFromBase64() {
        assertThat(LZString.decompressFromBase64(compressed), is(decompressed));
    }
    
    @Test
    public void testNullAndEmptyInput() {
        assertThat(LZString.compressToBase64(null), is(nullValue()));
        assertThat(LZString.compressToBase64(""), is(nullValue()));

        assertThat(LZString.decompressFromBase64(null), is(nullValue()));
        assertThat(LZString.decompressFromBase64(""), is(nullValue()));
    }
    
}
