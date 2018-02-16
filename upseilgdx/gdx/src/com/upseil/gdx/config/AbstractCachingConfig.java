package com.upseil.gdx.config;

import java.lang.reflect.Type;

import com.badlogic.gdx.utils.ObjectMap;
import com.upseil.gdx.util.DoubleBasedExpression;
import com.upseil.gdx.util.exception.NotImplementedException;

public class AbstractCachingConfig extends AbstractConfig {
    
    private enum ObjectType { Int, Float, Double, Boolean, String, DoubleBasedExpression, Enum }

    private ObjectMap<String, Object> valueCache;

    public AbstractCachingConfig(String path) {
        super(path);
    }

    public AbstractCachingConfig(RawConfig rawConfig) {
        super(rawConfig);
    }

    @Override
    public float getFloat(String key) {
        return (Float) getObject(key, ObjectType.Float);
    }

    @Override
    public double getDouble(String key) {
        return (Double) getObject(key, ObjectType.Double);
    }

    @Override
    public int getInt(String key) {
        return (Integer) getObject(key, ObjectType.Int);
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

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Enum<T>> T getEnum(String key, Class<T> type) {
        return (T) getObject(key, ObjectType.Enum, type);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getValue(String key, Class<T> type) {
        return (T) getObject(key, getObjectType(type));
    }
    
    private ObjectType getObjectType(Type type) {
        if (type == Integer.class || type == Integer.TYPE) return ObjectType.Int;
        if (type == Float.class || type == Float.TYPE) return ObjectType.Int;
        if (type == Double.class || type == Double.TYPE) return ObjectType.Double;
        if (type == Boolean.class || type == Boolean.TYPE) return ObjectType.Boolean;
        if (type == String.class) return ObjectType.String;
        if (type == DoubleBasedExpression.class) return ObjectType.DoubleBasedExpression;
        if (type == Enum.class) return ObjectType.Enum;
        throw new IllegalArgumentException("No " + ObjectType.class.getSimpleName() + " available for " + type);
    } 
    
    public void clear() {
        if (valueCache != null) valueCache.clear();
    }

    private Object getObject(String key, ObjectType type) {
        return getObject(key, type, null);
    }

    private Object getObject(String key, ObjectType type, Class<?> enumType) {
        if (valueCache == null) valueCache = new ObjectMap<>();
        Object value = valueCache.get(key);
        if (value == null) {
            value = getConfigValue(key, type, enumType);
            valueCache.put(key, value);
        }
        return value;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Object getConfigValue(String key, ObjectType type, Class<?> enumType) {
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
        case Float:
            return super.getFloat(key);
        case Int:
            return super.getInt(key);
        }
        throw new NotImplementedException("Can't get object value of type " + type);
    }
    
}
