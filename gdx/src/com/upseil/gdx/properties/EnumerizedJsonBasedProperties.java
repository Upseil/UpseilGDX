package com.upseil.gdx.properties;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.utils.JsonValue;

public abstract class EnumerizedJsonBasedProperties<E extends Enum<E>> extends AbstractJsonBasedProperties<E> {
    
    private final Map<String, E> keyUniverse;

    public EnumerizedJsonBasedProperties(JsonValue json, Class<E> keyType) {
        this(json, false, keyType);
    }

    public EnumerizedJsonBasedProperties(JsonValue json, boolean strict, Class<E> keyType) {
        super(json, strict);
        keyUniverse = new HashMap<>();
        for (E key : keyType.getEnumConstants()) {
            keyUniverse.put(keyToString(key).toLowerCase(), key);
        }
    }

    @Override
    protected String keyToString(E key) {
        return key.name();
    }

    @Override
    protected E stringToKey(String string) {
        return keyUniverse.get(string.toLowerCase());
    }
    
}
