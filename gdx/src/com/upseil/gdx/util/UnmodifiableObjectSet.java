package com.upseil.gdx.util;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;

public class UnmodifiableObjectSet<T> extends ObjectSet<T> {

    public UnmodifiableObjectSet(ObjectSet<T> set) {
        super(set);
        shrink(0);
    }

    @Override
    public boolean add(T key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addAll(Array<? extends T> array) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addAll(Array<? extends T> array, int offset, int length) {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addAll(T... array) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addAll(T[] array, int offset, int length) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addAll(ObjectSet<T> set) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(T key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear(int maximumCapacity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void ensureCapacity(int additionalCapacity) {
        throw new UnsupportedOperationException();
    }
    
}
