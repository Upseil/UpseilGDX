package com.upseil.gdx.util;

public class FloatFloatPair {
    
    protected float a;
    protected float b;
    
    public FloatFloatPair() { }
    
    public FloatFloatPair(float a, float b) {
        this.a = a;
        this.b = b;
    }

    public FloatFloatPair set(float a, float b) {
        this.a = a;
        this.b = b;
        return this;
    }

    public float getA() {
        return a;
    }
    
    public FloatFloatPair setA(float a) {
        this.a = a;
        return this;
    }

    public float getB() {
        return b;
    }
    
    public FloatFloatPair setB(float b) {
        this.b = b;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(a);
        result = prime * result + Float.floatToIntBits(b);
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
        FloatFloatPair other = (FloatFloatPair) obj;
        if (Float.floatToIntBits(a) != Float.floatToIntBits(other.a))
            return false;
        if (Float.floatToIntBits(b) != Float.floatToIntBits(other.b))
            return false;
        return true;
    }
    
}
