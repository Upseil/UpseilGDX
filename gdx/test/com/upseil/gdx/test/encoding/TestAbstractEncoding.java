package com.upseil.gdx.test.encoding;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.upseil.gdx.encoding.AbstractEncoding;

public class TestAbstractEncoding {
    
    private static final POJOEncoding encoding = new POJOEncoding();
    private static POJO pojo;
    
    @BeforeClass
    public static void setupPojo() {
        pojo = new POJO();
        pojo.setValue1(7);
        pojo.setValue2(11);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidEncodingInput() {
        encoding.toBytes(pojo, new byte[0]);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testInvalidDecodingInput() {
        encoding.fromBytes(new byte[0]);
    }
    
    @Test
    public void testEncoding() {
        String encoded = encoding.toBase64(pojo);
        POJO decoded = encoding.fromBase64(encoded);
        assertThat(decoded, is(pojo));
    }
    
    private static class POJOEncoding extends AbstractEncoding<POJO> {

        public POJOEncoding() {
            super(2 * 4);
        }

        @Override
        protected void writeToBuffer(POJO pojo) {
            buffer.putInt(pojo.getValue1());
            buffer.putInt(pojo.getValue2());
        }

        @Override
        protected POJO readFromBuffer() {
            POJO pojo = new POJO();
            pojo.setValue1(buffer.getInt());
            pojo.setValue2(buffer.getInt());
            return pojo;
        }
        
    }
    
    private static class POJO {

        private int value1;
        private int value2;
        
        public int getValue1() {
            return value1;
        }
        
        public void setValue1(int value1) {
            this.value1 = value1;
        }
        
        public int getValue2() {
            return value2;
        }
        
        public void setValue2(int value2) {
            this.value2 = value2;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + value1;
            result = prime * result + value2;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            POJO other = (POJO) obj;
            if (value1 != other.value1)
                return false;
            if (value2 != other.value2)
                return false;
            return true;
        }
        
    }
    
}
