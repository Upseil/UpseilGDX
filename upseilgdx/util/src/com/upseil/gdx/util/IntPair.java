package com.upseil.gdx.util;

public class IntPair<T> {
    
    protected int a;
    protected T b;
    
    public IntPair() { }
    
    public IntPair(int a, T b) {
        this.a = a;
        this.b = b;
    }

    public IntPair<T> set(int a, T b) {
        this.a = a;
        this.b = b;
        return this;
    }

    public int getA() {
        return a;
    }
    
    public IntPair<T> setA(int a) {
        this.a = a;
        return this;
    }

    public T getB() {
        return b;
    }
    
    public IntPair<T> setB(T b) {
        this.b = b;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + a;
        result = prime * result + ((b == null) ? 0 : b.hashCode());
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
        IntPair<?> other = (IntPair<?>) obj;
        if (a != other.a)
            return false;
        if (b == null) {
            if (other.b != null)
                return false;
        } else if (!b.equals(other.b))
            return false;
        return true;
    }
    
}
