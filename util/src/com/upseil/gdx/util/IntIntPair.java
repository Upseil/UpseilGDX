package com.upseil.gdx.util;

public class IntIntPair {
    
    protected int a;
    protected int b;
    
    public IntIntPair() { }
    
    public IntIntPair(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public IntIntPair set(int a, int b) {
        this.a = a;
        this.b = b;
        return this;
    }

    public int getA() {
        return a;
    }
    
    public IntIntPair setA(int a) {
        this.a = a;
        return this;
    }

    public int getB() {
        return b;
    }
    
    public IntIntPair setB(int b) {
        this.b = b;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + a;
        result = prime * result + b;
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
        IntIntPair other = (IntIntPair) obj;
        if (a != other.a)
            return false;
        if (b != other.b)
            return false;
        return true;
    }
    
}
