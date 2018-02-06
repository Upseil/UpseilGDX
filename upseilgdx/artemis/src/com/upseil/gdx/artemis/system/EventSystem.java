package com.upseil.gdx.artemis.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.upseil.gdx.artemis.component.EventComponent;
import com.upseil.gdx.event.Event;
import com.upseil.gdx.event.EventHandler;
import com.upseil.gdx.event.EventType;

public class EventSystem extends IteratingSystem implements RequiresStepping {
	
	private ComponentMapper<EventComponent> mapper;
	
	private final ObjectMap<EventType<?>, ObjectSet<EventHandler<?>>> handlers; 

	public EventSystem() {
		super(Aspect.all(EventComponent.class));
		handlers = new ObjectMap<>();
	}

	@Override
	protected void process(int entityId) {
		EventComponent event = mapper.get(entityId);
		for (EventHandler<?> handler : handlers.get(event.getType())) {
			forwardEvent(event, handler);
		}
	}
    
    @SuppressWarnings("unchecked")
    private <T extends Event<T>> void forwardEvent(EventComponent event, EventHandler<T> handler) {
        T actualEvent = (T) event.get();
        handler.accept(actualEvent);
    }
    
    public <T extends Event<T>> void registerHandler(EventType<T> type, EventHandler<T> handler) {
        ObjectSet<EventHandler<?>> typeHandlers = handlers.get(type);
        if (typeHandlers == null) {
            typeHandlers = new ObjectSet<>();
            handlers.put(type, typeHandlers);
        }
        typeHandlers.add(handler);
    }
    
    public void unregisterHandler(EventHandler<?> handler) {
        for (ObjectSet<EventHandler<?>> typeHandlers : handlers.values()) {
            typeHandlers.remove(handler);
        }
    }

}
