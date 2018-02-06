package com.upseil.gdx.event;

import com.upseil.gdx.pool.AbstractPooled;

public abstract class AbstractEvent<T extends AbstractEvent<T>> extends AbstractPooled<T> implements Event<T> {
	
	private EventType<T> type;

	@Override
	public EventType<T> getType() {
		if (type == null) setType();
		return type;
	}
	
	protected abstract void setType();
	
	@Override
	public void reset() {
		super.reset();
		type = null;
	}

}
