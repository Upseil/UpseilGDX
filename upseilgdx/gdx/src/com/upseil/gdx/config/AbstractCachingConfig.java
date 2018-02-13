package com.upseil.gdx.config;

import com.badlogic.gdx.utils.ObjectFloatMap;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.upseil.gdx.util.DoubleBasedExpression;
import com.upseil.gdx.util.exception.NotImplementedException;

public class AbstractCachingConfig extends AbstractConfig {
    
    private enum ObjectType { Double, Boolean, String, DoubleBasedExpression, Enum }

    private ObjectIntMap<String> intCache;
    private ObjectFloatMap<String> floatCache;
    private ObjectMap<String, Object> objectCache;

    public AbstractCachingConfig(String path) {
        super(path);
    }

    public AbstractCachingConfig(RawConfig rawConfig) {
        super(rawConfig);
    }

    @Override
    public float getFloat(String key) {
        if (floatCache == null) floatCache = new ObjectFloatMap<>();
        if (!floatCache.containsKey(key)) {
            float value = super.getFloat(key);
            floatCache.put(key, value);
        }
        return floatCache.get(key, 0);
    }

    @Override
    public double getDouble(String key) {
        return (Double) getObject(key, ObjectType.Double);
    }

    @Override
    public int getInt(String key) {
        if (intCache == null) intCache = new ObjectIntMap<>();
        if (!intCache.containsKey(key)) {
            int value = super.getInt(key);
            intCache.put(key, value);
        }
        return intCache.get(key, 0);
    }

    @Override
    public boolean getBoolean(String key) {
        return (Boolean) getObject(key, ObjectType.Boolean);
    }

    @Override
    public String getString(String key) {
        return (String) getObject(key, ObjectType.String);
    }

    @Override
    public DoubleBasedExpression getExpression(String key) {
        return (DoubleBasedExpression) getObject(key, ObjectType.DoubleBasedExpression);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Enum<T>> T getEnum(String key, Class<T> type) {
        return (T) getObject(key, ObjectType.Enum, type);
    }
    
    public void clear() {
        if (intCache != null) intCache.clear();
        if (floatCache != null) floatCache.clear();
        if (objectCache != null) objectCache.clear();
    }

    private Object getObject(String key, ObjectType type) {
        return getObject(key, type, null);
    }

    private Object getObject(String key, ObjectType type, Class<?> enumType) {
        if (objectCache == null) objectCache = new ObjectMap<>();
        Object value = objectCache.get(key);
        if (value == null) {
            value = getObjectValue(key, type, enumType);
            objectCache.put(key, value);
        }
        return value;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Object getObjectValue(String key, ObjectType type, Class<?> enumType) {
        switch (type) {
        case Boolean:
            return super.getBoolean(key);
        case Double:
            return super.getDouble(key);
        case DoubleBasedExpression:
            return super.getExpression(key);
        case Enum:
            return super.getEnum(key, (Class<Enum>) enumType);
        case String:
            return super.getString(key);
        }
        throw new NotImplementedException("Can't get object value of type " + type);
    }
    
}
