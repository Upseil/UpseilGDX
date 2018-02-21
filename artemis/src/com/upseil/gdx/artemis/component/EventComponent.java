package com.upseil.gdx.artemis.component;

import com.artemis.PooledComponent;
import com.upseil.gdx.event.Event;
import com.upseil.gdx.event.EventType;

public class EventComponent extends PooledComponent {
	
	private Event<?> event;
	private boolean immediate;
	
	public Event<?> get() {
		return event;
	}
	
	public void set(Event<?> event) {
		this.event = event;
	}
	
	public EventType<?> getType() {
		return event.getType();
	}
	
	public boolean isImmediate() {
        return immediate;
    }

    public void setImmediate(boolean immediate) {
        this.immediate = immediate;
    }

    @Override
	public String toString() {
		return event == null ? "null" : event.toString();
	}

	@Override
	protected void reset() {
		event.free();
		event = null;
		immediate = false;
	}

}