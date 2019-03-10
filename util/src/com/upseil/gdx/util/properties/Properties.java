package com.upseil.gdx.util.properties;

import com.upseil.gdx.util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public interface Properties<K> {
    
    // ----- Factory Methods ------------------------------------------------------------------------------------------------------------------------
    
    static Properties<String> fromPropertiesText(String text) {
        return fromPropertiesText(text, false);
    }

    static Properties<String> fromPropertiesText(String text, boolean strict) {
        return fromPropertiesLines(Arrays.asList(text.split("\\n")), strict);
    }

    static Properties<String> fromPropertiesLines(Iterable<String> lines) {
        return fromPropertiesLines(lines, false);
    }
    
    static Properties<String> fromPropertiesLines(Iterable<String> lines, boolean strict) {
        ModifiableProperties<String> properties = new MapBasedProperties<>(strict);
        for (String line : lines) {
            Pair<String, String> property = parseLine(line);
            if (property != null) {
                properties.put(property.getA(), property.getB());
            }
        }
        return properties;
    }

    static <E extends Enum<E>> Properties<E> fromPropertiesText(String text, Class<E> keyType) {
        return fromPropertiesText(text, keyType, false);
    }

    static <E extends Enum<E>> Properties<E> fromPropertiesText(String text, Class<E> keyType, boolean strict) {
        return fromPropertiesLines(Arrays.asList(text.split("\\n")), keyType, strict);
    }

    static <E extends Enum<E>> Properties<E> fromPropertiesLines(Iterable<String> lines, Class<E> keyType) {
        return fromPropertiesLines(lines, keyType, false);
    }

    static <E extends Enum<E>> Properties<E> fromPropertiesLines(Iterable<String> lines, Class<E> keyType, boolean strict) {
        E[] enumValues = keyType.getEnumConstants();
        Map<String, E> keyUniverse = new HashMap<>();
        for (E enumValue : enumValues) {
            keyUniverse.put(enumValue.name().toLowerCase(), enumValue);
        }
        
        ModifiableProperties<E> properties = new MapBasedProperties<>(strict);
        for (String line : lines) {
            Pair<String, String> property = parseLine(line);
            if (property != null) {
                String keyString = property.getA().toLowerCase();
                if (keyUniverse.containsKey(keyString))  {
                    properties.put(keyUniverse.get(keyString), property.getB());
                }
            }
        }
        return properties;
    }

    static Pair<String, String> parseLine(String line) {
        String trimmedLine = line.trim();
        if (trimmedLine.startsWith("#") || trimmedLine.startsWith("!")) {
            return null;
        }

        String[] property = line.split("=");
        if (property.length != 2) {
            return null;
        }
        return new Pair<>(property[0].trim(), property[1].trim());
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
