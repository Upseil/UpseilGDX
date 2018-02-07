package com.upseil.gdx.artemis.component;

import com.artemis.PooledComponent;
import com.upseil.gdx.event.Event;
import com.upseil.gdx.event.EventType;

public class EventComponent extends PooledComponent {
	
	private Event<?> event;
	private boolean fireImmediate;
	
	public Event<?> get() {
		return event;
	}
	
	public void set(Event<?> event) {
		this.event = event;
	}
	
	public EventType<?> getType() {
		return event.getType();
	}
	
	public boolean isFireImmediate() {
        return fireImmediate;
    }

    public void setFireImmediate(boolean fireImmediate) {
        this.fireImmediate = fireImmediate;
    }

    @Override
	public String toString() {
		return event == null ? "null" : event.toString();
	}

	@Override
	protected void reset() {
		event.free();
		event = null;
		fireImmediate = false;
	}

}