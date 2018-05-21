package com.upseil.gdx.util.properties;

public interface ModifiableProperties<K> extends Properties<K> {
    
    boolean remove(K key);
    void clear();
    
    void put(K key, String value);
    
    default void put(K key, int value) {
        put(key, Integer.toString(value));
    }
    
    default void put(K key, long value) {
        put(key, Long.toString(value));
    }
    
    default void put(K key, float value) {
        put(key, Float.toString(value));
    }
    
    default void put(K key, double value) {
        put(key, Double.toString(value));
    }
    
    default void put(K key, boolean value) {
        put(key, Boolean.toString(value));
    }
    
    default <E extends Enum<E>> void put(K key, E value) {
        put(key, value.name());
    }
    
}
