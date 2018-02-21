package com.upseil.gdx.event;

public class EventType<T extends Event<T>> {
	
	private final String name;

	public EventType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name != null ? name : getClass().getSimpleName();
	}

}
