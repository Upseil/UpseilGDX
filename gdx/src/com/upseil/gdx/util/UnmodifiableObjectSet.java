package com.upseil.gdx.util;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;

public class UnmodifiableObjectSet<T> extends ObjectSet<T> {

    private final ObjectSet<T> set;

    public UnmodifiableObjectSet(ObjectSet<T> set) {
        this.set = set;
    }

    @Override
    public boolean contains(T key) {
        return set.contains(key);
    }

    @Override
    public T get(T key) {
        return set.get(key);
    }

    @Override
    public T first() {
        return set.first();
    }

    @Override
    public int hashCode() {
        return set.hashCode() + UnmodifiableObjectSet.class.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UnmodifiableObjectSet)) return false;
        
        return set.equals(obj);
    }

    @Override
    public String toString() {
        return set.toString();
    }

    @Override
    public String toString(String separator) {
        return set.toString(separator);
    }

    @Override
    public ObjectSetIterator<T> iterator() {
        return set.iterator();
    }

    @Override
    public void shrink(int maximumCapacity) {
        throw new UnsupportedOperationException();
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
