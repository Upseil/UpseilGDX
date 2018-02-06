package com.upseil.gdx.artemis.component;

import com.artemis.PooledComponent;
import com.artemis.World;
import com.upseil.gdx.event.Event;
import com.upseil.gdx.event.EventType;

public class EventComponent extends PooledComponent {
	
	private Event<?> event;
	
	public Event<?> get() {
		return event;
	}
	
	public void set(Event<?> event) {
		this.event = event;
	}
	
	public EventType<?> getType() {
		return event.getType();
	}
	
	@Override
	public String toString() {
		return event == null ? "null" : event.toString();
	}

	@Override
	protected void reset() {
		event.free();
		event = null;
	}
	
	public static void fire(World world, Event<?> event) {
		world.createEntity().edit().create(EventComponent.class).set(event);
	}

}
