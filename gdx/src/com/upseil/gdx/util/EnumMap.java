package com.upseil.gdx.util;

import com.badlogic.gdx.utils.reflect.ClassReflection;

// TODO Implement Map<K, V>, Tests
public class EnumMap<K extends Enum<K>, V> {
    
    private final Class<K> keyType;
    private final K[] keyUniverse;
    
    private final Object[] values;
    private int size;
    
    public EnumMap(Class<K> keyType) {
        this.keyType = keyType;
        keyUniverse = getKeyUniverse(keyType);
        values = new Object[keyUniverse.length];
    }

    public EnumMap(EnumMap<K, V> source) {
        keyType = source.keyType;
        keyUniverse = getKeyUniverse(keyType);
        values = new Object[keyUniverse.length];
        putAll(source);
    }
    
    public boolean containsKey(K key) {
        return values[key.ordinal()] != null;
    }
    
    public boolean containsValue(V value) {
        return containsValue(value, true);
    }
    
    public boolean containsValue(V value, boolean identity) {
        Object maskedValue = maskNull(value);

        if (identity) {
            for (int index = 0; index < keyUniverse.length; index++) {
                if (values[index] == maskedValue) {
                    return true;
                }
            }
        } else {
            for (int index = 0; index < keyUniverse.length; index++) {
                if (maskedValue.equals(values[index])) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public Class<K> getKeyType() {
        return keyType;
    }
    
    public V get(K key) {
        return unmaskNull(values[key.ordinal()]);
    }

    public V put(K key, V value) {
        int index = key.ordinal();
        Object oldValue = values[index];
        values[index] = maskNull(value);
        if (oldValue == null) size++;
        return unmaskNull(oldValue);
    }
    
    public void putAll(EnumMap<K, V> map) {
        if (map.size == 0) return;
        
        for (int index = 0; index < keyUniverse.length; index++) {
            Object mapValue = map.values[index];
            if (mapValue != null) {
                if (values[index] == null) size++;
                values[index] = mapValue;
            }
        }
    }
    
    public V remove(K key) {
        int index = key.ordinal();
        Object oldValue = values[index];
        values[index] = null;
        if (oldValue != null) size--;
        return unmaskNull(oldValue);
    }
    
    public void clear() {
        for (int index = 0; index < keyUniverse.length; index++) {
            values[index] = null;
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
            if (values[index] != null) {
                result += (keyUniverse[index].hashCode() ^ values[index].hashCode());
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
        
        EnumMap<?, ?> other = (EnumMap<?, ?>) obj;
        if (keyType != other.keyType)
            return size == 0 && other.size == 0;

        for (int index = 0; index < keyUniverse.length; index++) {
            Object value = values[index];
            Object otherValue = other.values[index];
            if (value != otherValue && (otherValue != null && !value.equals(otherValue))) {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private static <K extends Enum<K>> K[] getKeyUniverse(Class<K> keyType) {
        return (K[]) ClassReflection.getEnumConstants(keyType);
    }

    private static final Object NULL = new Object() {
        public int hashCode() {
            return 0;
        }
        public String toString() {
            return "com.upseil.gdx.util.EnumMap.NULL";
        }
    };

    private static <V> Object maskNull(V value) {
        return (value == null ? NULL : value);
    }

    @SuppressWarnings("unchecked")
    private static <V> V unmaskNull(Object value) {
        return (V)(value == NULL ? null : value);
    }
    
}
