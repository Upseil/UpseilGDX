package com.upseil.gdx.artemis.event;

import com.artemis.utils.IntBag;
import com.upseil.gdx.event.AbstractEvent;
import com.upseil.gdx.event.EventType;

public abstract class AbstractEntityCollectionEvent<T extends AbstractEntityCollectionEvent<T>> extends AbstractEvent<T> {

    private final IntBag entities;
    
    public AbstractEntityCollectionEvent(EventType<T> type) {
        super(type);
        entities = new IntBag(16);
    }
    
    public IntBag getEntities() {
        return entities;
    }
    
    public void addEntity(int entityId) {
        entities.add(entityId);
    }
    
    public int size() {
        return entities.size();
    }
    
    @Override
    public void reset() {
        super.reset();
        entities.clear();
    }
    
}
