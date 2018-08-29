package com.upseil.gdx.util;

// TODO Implement Iterable, Tests
public class EnumDoubleMap<K extends Enum<K>> extends AbstractEnumMap<K> {
    
    private final boolean[] contains;
    private final double[] values;
    private int size;
    
    public EnumDoubleMap(EnumDoubleMap<K> source) {
        this(source.getKeyType());
        putAll(source);
    }
    
    public EnumDoubleMap(Class<K> keyType) {
        super(keyType);
        contains = new boolean[keyUniverseSize()];
        values = new double[keyUniverseSize()];
    }
    
    public boolean containsKey(K key) {
        return contains[key.ordinal()];
    }
    
    public boolean containsValue(double value) {
        for (int index = 0; index < keyUniverseSize(); index++) {
            if (contains[index] && values[index] == value) {
                return true;
            }
        }
        return false;
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

    public void put(K key, double value) {
        int index = key.ordinal();
        if (!contains[index]) size++;
        contains[index] = true;
        values[index] = value;
    }
    
    public void putAll(EnumDoubleMap<K> map) {
        for (int index = 0; index < keyUniverseSize(); index++) {
            if (map.contains[index]) {
                put(getKeyFromUniverse(index), map.values[index]);
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
        GDXArrays.clear(contains);
        GDXArrays.clear(values);
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
        for (int i = 0; i < keyUniverseSize(); i++) {
            if (!first) {
                builder.append(", ");
            }
            K key = getKeyFromUniverse(i);
            builder.append(key).append("=").append(get(key));
            first = false;
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (int index = 0; index < keyUniverseSize(); index++) {
            if (contains[index]) {
                result += (getKeyFromUniverse(index).hashCode() ^ Double.hashCode(values[index]));
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
        if (getKeyType() != other.getKeyType())
            return size == 0 && other.size == 0;

        for (int index = 0; index < keyUniverseSize(); index++) {
            if (contains[index] != other.contains[index] ||
                (contains[index] && values[index] != other.values[index])) {
                return false;
            }
        }
        return true;
    }
    
}
