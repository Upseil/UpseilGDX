package com.upseil.gdx.util;

public class IntFloatPair {
    
    protected int a;
    protected float b;
    
    public IntFloatPair() { }
    
    public IntFloatPair(int a, float b) {
        this.a = a;
        this.b = b;
    }

    public IntFloatPair set(int a, float b) {
        this.a = a;
        this.b = b;
        return this;
    }

    public int getA() {
        return a;
    }
    
    public IntFloatPair setA(int a) {
        this.a = a;
        return this;
    }

    public float getB() {
        return b;
    }
    
    public IntFloatPair setB(float b) {
        this.b = b;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + a;
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
        IntFloatPair other = (IntFloatPair) obj;
        if (a != other.a)
            return false;
        if (Float.floatToIntBits(b) != Float.floatToIntBits(other.b))
            return false;
        return true;
    }
    
}
