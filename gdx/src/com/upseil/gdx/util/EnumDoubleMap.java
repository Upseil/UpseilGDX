package com.upseil.gdx.util;

import com.badlogic.gdx.utils.reflect.ClassReflection;

// TODO Implement Iterable, Tests
public class EnumDoubleMap<K extends Enum<K>> {
    
    private final Class<K> keyType;
    private final K[] keyUniverse;
    
    private final boolean[] contains;
    private final double[] values;
    private int size;
    
    public EnumDoubleMap(Class<K> keyType) {
        this.keyType = keyType;
        keyUniverse = getKeyUniverse(keyType);
        contains = new boolean[keyUniverse.length];
        values = new double[keyUniverse.length];
    }

    public EnumDoubleMap(EnumDoubleMap<K> source) {
        keyType = source.keyType;
        keyUniverse = getKeyUniverse(keyType);
        contains = new boolean[keyUniverse.length];

        values = new double[keyUniverse.length];
        putAll(source);
    }
    
    public boolean containsKey(K key) {
        return contains[key.ordinal()];
    }
    
    public boolean containsValue(double value) {
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

    public float getAsFloat(K key) {
        return (float) get(key, 0);
    }

    public float getAsFloat(K key, float defaultValue) {
        return (float) get(key, defaultValue);
    }
    
    public double get(K key) {
        return get(key, 0);
    }
    
    public double get(K key, double defaultValue) {
        int index = key.ordinal();
        if (!contains[index]) return defaultValue;
        return values[index];
    }
    
    public void increment(K key) {
        increment(key, 1);
    }
    
    public void increment(K key, double by) {
        put(key, get(key) + by);
    }

    public void decrement(K key) {
        decrement(key, 1);
    }

    public void decrement(K key, double by) {
        put(key, get(key) - by);
    }

    // TODO Return old value?
    public void put(K key, double value) {
        int index = key.ordinal();
        if (!contains[index]) size++;
        contains[index] = true;
        values[index] = value;
    }
    
    public void putAll(EnumDoubleMap<K> map) {
        for (int index = 0; index < keyUniverse.length; index++) {
            if (map.contains[index]) {
                put(keyUniverse[index], map.values[index]);
            }
        }
    }

    public double remove(K key) {
        return remove(key, 0);
    }
    
    public double remove(K key, double defaultValue) {
        int index = key.ordinal();
        if (!contains[index]) return defaultValue;
        
        double oldValue = values[index];
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
                result += (keyUniverse[index].hashCode() ^ Double.hashCode(values[index]));
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
        
        EnumDoubleMap<?> other = (EnumDoubleMap<?>) obj;
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
