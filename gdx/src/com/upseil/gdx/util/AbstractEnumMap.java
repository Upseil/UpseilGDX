package com.upseil.gdx.util;

import com.badlogic.gdx.utils.reflect.ClassReflection;

public abstract class AbstractEnumMap<K extends Enum<K>> {
    
    private final Class<K> keyType;
    private final Object[] keyUniverse;

    protected AbstractEnumMap(Class<K> keyType) {
        this.keyType = keyType;
        keyUniverse = ClassReflection.getEnumConstants(keyType);
    }
    
    public Class<K> getKeyType() {
        return keyType;
    }
    
    protected final Object[] getKeyUniverse() {
        return keyUniverse;
    }
    
    @SuppressWarnings("unchecked")
    protected final K getKeyFromUniverse(int index) {
        return (K) keyUniverse[index];
    }
    
    protected final int keyUniverseSize() {
        return keyUniverse.length;
    }
    
}
