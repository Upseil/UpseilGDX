package com.upseil.gdx.event;

import com.upseil.gdx.pool.Pooled;

public interface Event<T extends Event<T>> extends Pooled<T> {
	
	public EventType<T> getType();
	
}
