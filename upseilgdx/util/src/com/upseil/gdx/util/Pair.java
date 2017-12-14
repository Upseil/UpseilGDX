package com.upseil.gdx.util;

public class Pair<A, B> {
    
    private A a;
    private B b;
    
    public Pair() { }
    
    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public Pair<A, B> set(A a, B b) {
        this.a = a;
        this.b = b;
        return this;
    }

    public A getA() {
        return a;
    }
    
    public Pair<A, B> setA(A a) {
        this.a = a;
        return this;
    }

    public B getB() {
        return b;
    }
    
    public Pair<A, B> setB(B b) {
        this.b = b;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((a == null) ? 0 : a.hashCode());
        result = prime * result + ((b == null) ? 0 : b.hashCode());
        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Pair) {
            Pair pair = (Pair) obj;
            if (a != null ? !a.equals(pair.a) : pair.a != null) return false;
            if (b != null ? !b.equals(pair.b) : pair.b != null) return false;
            return true;
        }
        return false;
    }
    
}
