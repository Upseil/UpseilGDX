package com.upseil.gdx.util;

// TODO Implement Map<K, V>, Tests
public class EnumMap<K extends Enum<K>, V> extends AbstractEnumMap<K> {
    
    private final Object[] values;
    private int size;

    public EnumMap(EnumMap<K, V> source) {
        this(source.getKeyType());
        putAll(source);
    }
    
    public EnumMap(Class<K> keyType) {
        super(keyType);
        values = new Object[keyUniverseSize()];
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
            for (int index = 0; index < keyUniverseSize(); index++) {
                if (values[index] == maskedValue) {
                    return true;
                }
            }
        } else {
            for (int index = 0; index < keyUniverseSize(); index++) {
                if (maskedValue.equals(values[index])) {
                    return true;
                }
            }
        }
        return false;
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
        
        for (int index = 0; index < keyUniverseSize(); index++) {
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
            if (values[index] != null) {
                result += (getKeyFromUniverse(index).hashCode() ^ values[index].hashCode());
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
        if (getKeyType() != other.getKeyType())
            return size == 0 && other.size == 0;

        for (int index = 0; index < keyUniverseSize(); index++) {
            Object value = values[index];
            Object otherValue = other.values[index];
            if (value != otherValue && (otherValue != null && !value.equals(otherValue))) {
                return false;
            }
        }
        return true;
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
