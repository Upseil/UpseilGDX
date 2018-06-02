package com.upseil.gdx.test.encoding;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.upseil.gdx.encoding.Base64;

public class TestBase64 {
    
    @Test
    public void testNullEncoding() {
        assertThat(Base64.toBase64(null), nullValue());
        assertThat(Base64.fromBase64(null), nullValue());
    }
    
    @Test
    public void testEmptyEncoding() {
        assertThat(Base64.toBase64(new byte[0]), is(emptyString()));
        assertThat(Base64.fromBase64(""), is(new byte[0]));
    }
    
    @Test
    public void testByteArrayEncoding() throws UnsupportedEncodingException {
        String text = "Hello Base64!";
        String encoded = Base64.toBase64(text.getBytes());
        String decoded = new String(Base64.fromBase64(encoded), "UTF-8");
        assertThat(decoded, is(text));
    }
    
    @Test
    public void testLongEncoding() {
        long value = 1337;
        String encoded = Base64.toBase64(value);
        long decoded = Base64.longFromBase64(encoded);
        assertThat(decoded, is(value));
    }
    
}
