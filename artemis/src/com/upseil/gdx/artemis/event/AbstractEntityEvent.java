package com.upseil.gdx.artemis.event;

import com.upseil.gdx.event.AbstractEvent;

public abstract class AbstractEntityEvent<T extends AbstractEntityEvent<T>> extends AbstractEvent<T> {

	private int entityId = -1;

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}
	
	@Override
	public void reset() {
		super.reset();
		entityId = -1;
	}

}
