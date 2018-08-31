package com.upseil.gdx.util.properties;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public interface Properties<K> {
    
    // ----- Factory Methods ------------------------------------------------------------------------------------------------------------------------
    
    static Properties<String> fromPropertiesText(String text) {
        return fromPropertiesLines(Arrays.asList(text.split("\\n")));
    }
    
    static Properties<String> fromPropertiesLines(Iterable<String> lines) {
        ModifiableProperties<String> properties = new MapBasedProperties<>();
        for (String line : lines) {
            String trimmedLine = line.trim();
            if (trimmedLine.startsWith("#") || trimmedLine.startsWith("!")) {
                continue;
            }
            
            String[] property = line.split("=");
            if (property.length == 2) {
                properties.put(property[0].trim(), property[1].trim());
            }
        }
        return properties;
    }
    
    static <E extends Enum<E>> Properties<E> fromPropertiesText(String text, Class<E> type) {
        return fromPropertiesLines(Arrays.asList(text.split("\\n")), type);
    }
    
    static <E extends Enum<E>> Properties<E> fromPropertiesLines(Iterable<String> lines, Class<E> type) {
        E[] enumValues = type.getEnumConstants();
        Map<String, E> keyUniverse = new HashMap<>();
        for (E enumValue : enumValues) {
            keyUniverse.put(enumValue.name().toLowerCase(), enumValue);
        }
        
        ModifiableProperties<E> properties = new MapBasedProperties<>();
        for (String line : lines) {
            String trimmedLine = line.trim();
            if (trimmedLine.startsWith("#") || trimmedLine.startsWith("!")) {
                continue;
            }
            
            String[] property = line.split("=");
            String keyString = property[0].trim().toLowerCase();
            if (property.length == 2 && keyUniverse.containsKey(keyString)) {
                properties.put(keyUniverse.get(keyString), property[1].trim());
            }
        }
        return properties;
    }
    
    // ----- Methods --------------------------------------------------------------------------------------------------------------------------------

    boolean contains(K key);
    boolean isStrict();
    Iterable<K> keys();
    
    String get(K key, String defaultValue);
    default String get(K key) {
        if (isStrict() && !contains(key)) {
            throw new IllegalArgumentException("Element for key not found: " + key);
        }
        return get(key, null);
    }
    
    // ----- Type Specific Getters ------------------------------------------------------------------------------------------------------------------
    
    default int getInt(K key) {
        if (isStrict() && !contains(key)) {
            throw new IllegalArgumentException("Element for key not found: " + key);
        }
        return getInt(key, 0);
    }
    default int getInt(K key, int defaultValue) {
        String value = get(key);
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    default long getLong(K key) {
        if (isStrict() && !contains(key)) {
            throw new IllegalArgumentException("Element for key not found: " + key);
        }
        return getLong(key, 0);
    }
    default long getLong(K key, long defaultValue) {
        String value = get(key);
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    default float getFloat(K key) {
        if (isStrict() && !contains(key)) {
            throw new IllegalArgumentException("Element for key not found: " + key);
        }
        return getFloat(key, 0);
    }
    default float getFloat(K key, float defaultValue) {
        String value = get(key);
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    default double getDouble(K key) {
        if (isStrict() && !contains(key)) {
            throw new IllegalArgumentException("Element for key not found: " + key);
        }
        return getDouble(key, 0);
    }
    default double getDouble(K key, double defaultValue) {
        String value = get(key);
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    default boolean getBoolean(K key) {
        if (isStrict() && !contains(key)) {
            throw new IllegalArgumentException("Element for key not found: " + key);
        }
        return getBoolean(key, false);
    }
    default boolean getBoolean(K key, boolean defaultValue) {
        String value = get(key);
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        
        return Boolean.parseBoolean(value);
    }
    
    default <E extends Enum<E>> E getEnum(K key, Class<E> type) {
        if (isStrict() && !contains(key)) {
            throw new IllegalArgumentException("Element for key not found: " + key);
        }
        return getEnum(key, type, null);
    }
    default <E extends Enum<E>> E getEnum(K key, Class<E> type, E defaultValue) {
        String value = get(key);
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        
        try {
            return Enum.valueOf(type, value);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }
    
}
