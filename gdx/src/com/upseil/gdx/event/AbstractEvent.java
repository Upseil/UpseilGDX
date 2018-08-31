package com.upseil.gdx.event;

import com.upseil.gdx.pool.AbstractPooled;

public abstract class AbstractEvent<T extends AbstractEvent<T>> extends AbstractPooled<T> implements Event<T> {
	
	protected final EventType<T> type;

	protected AbstractEvent(EventType<T> type) {
        this.type = type;
    }

    @Override
	public EventType<T> getType() {
		return type;
	}
	
	@Override
	public String toString() {
	    return getType().getName();
	}

}
