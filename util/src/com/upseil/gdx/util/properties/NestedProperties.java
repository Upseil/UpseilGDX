package com.upseil.gdx.util.properties;

public interface NestedProperties<K> extends Properties<K> {
    
    NestedProperties<K> getParent();
    NestedProperties<K> getChild(K key);
    Iterable<NestedProperties<K>> getChildren();
    
}
