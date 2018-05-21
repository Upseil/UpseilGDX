package com.upseil.gdx.util.properties;

import java.util.HashMap;
import java.util.Map;

public class NestedCachingProperties<K> extends CachingProperties<K> implements NestedProperties<K> {
    
    private final NestedProperties<K> properties;
    
    private final NestedCachingProperties<K> parent;
    private final Map<K, NestedProperties<K>> children;

    public NestedCachingProperties(NestedProperties<K> properties) {
        this(properties, null);
    }

    private NestedCachingProperties(NestedProperties<K> properties, NestedCachingProperties<K> parent) {
        super(properties);
        this.properties = properties;
        this.parent = parent;
        children = new HashMap<>();
    }

    @Override
    public NestedProperties<K> getParent() {
        return parent;
    }
    
    @Override
    public NestedProperties<K> getChild(K key) {
        NestedProperties<K> child = children.get(key);
        if (child == null) {
            child = createChild(key);
        }
        return child;
    }
    
    @Override
    public Iterable<NestedProperties<K>> getChildren() {
        for (K key : properties.keys()) {
            if (!children.containsKey(key)) {
                createChild(key);
            }
        }
        return children.values();
    }

    private NestedCachingProperties<K> createChild(K key) {
        NestedCachingProperties<K> child = null;
        NestedProperties<K> propertiesChild = properties.getChild(key);
        if (propertiesChild != null) {
            child = new NestedCachingProperties<>(propertiesChild, this);
            children.put(key, child);
        }
        return child;
    }
    
}
