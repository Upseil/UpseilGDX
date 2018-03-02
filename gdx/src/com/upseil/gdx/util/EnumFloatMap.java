package com.upseil.gdx.util;

import com.badlogic.gdx.utils.reflect.ClassReflection;

// TODO Implement Iterable, Tests
public class EnumFloatMap<K extends Enum<K>> {
    
    private final Class<K> keyType;
    private final K[] keyUniverse;
    
    private final boolean[] contains;
    private final float[] values;
    private int size;
    
    public EnumFloatMap(Class<K> keyType) {
        this.keyType = keyType;
        keyUniverse = getKeyUniverse(keyType);
        contains = new boolean[keyUniverse.length];
        values = new float[keyUniverse.length];
    }

    public EnumFloatMap(EnumFloatMap<K> source) {
        keyType = source.keyType;
        keyUniverse = getKeyUniverse(keyType);
        contains = new boolean[keyUniverse.length];

        values = new float[keyUniverse.length];
        putAll(source);
    }
    
    public boolean containsKey(K key) {
        return contains[key.ordinal()];
    }
    
    public boolean containsValue(float value) {
        for (int index = 0; index < keyUniverse.length; index++) {
            if (contains[index] && values[index] == value) {
                return true;
            }
        }
        return false;
    }
    
    public Class<K> getKeyType() {
        return keyType;
    }
    
    public float get(K key) {
        return get(key, 0);
    }
    
    public float get(K key, float defaultValue) {
        int index = key.ordinal();
        if (!contains[index]) return defaultValue;
        return values[index];
    }
    
    public void increment(K key) {
        increment(key, 1);
    }
    
    public void increment(K key, float by) {
        put(key, get(key) + by);
    }

    public void decrement(K key) {
        decrement(key, 1);
    }

    public void decrement(K key, float by) {
        put(key, get(key) - by);
    }

    // TODO Return old value?
    public void put(K key, float value) {
        int index = key.ordinal();
        if (!contains[index]) size++;
        contains[index] = true;
        values[index] = value;
    }
    
    public void putAll(EnumFloatMap<K> map) {
        for (int index = 0; index < keyUniverse.length; index++) {
            if (map.contains[index]) {
                put(keyUniverse[index], map.values[index]);
            }
        }
    }

    public float remove(K key) {
        return remove(key, 0);
    }
    
    public float remove(K key, float defaultValue) {
        int index = key.ordinal();
        if (!contains[index]) return defaultValue;
        
        float oldValue = values[index];
        contains[index] = false;
        values[index] = 0;
        size--;
        return oldValue;
    }
    
    public void clear() {
        for (int index = 0; index < keyUniverse.length; index++) {
            contains[index] = false;
            values[index] = 0;
        }
        size = 0;
    }
    
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(getClass().getSimpleName()).append("[");
        boolean first = true;
        for (K key : keyUniverse) {
            if (!first) {
                builder.append(", ");
            }
            builder.append(key).append("=").append(get(key));
            first = false;
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (int index = 0; index < keyUniverse.length; index++) {
            if (contains[index]) {
                result += (keyUniverse[index].hashCode() ^ Float.hashCode(values[index]));
            }
        }
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
        
        EnumFloatMap<?> other = (EnumFloatMap<?>) obj;
        if (keyType != other.keyType)
            return size == 0 && other.size == 0;

        for (int index = 0; index < keyUniverse.length; index++) {
            if (contains[index] != other.contains[index] ||
                (contains[index] && values[index] != other.values[index])) {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private static <K extends Enum<K>> K[] getKeyUniverse(Class<K> keyType) {
        return (K[]) ClassReflection.getEnumConstants(keyType);
    }
    
}
