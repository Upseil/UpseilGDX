package com.upseil.gdx.artemis.system;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.upseil.gdx.artemis.component.EventComponent;
import com.upseil.gdx.event.Event;
import com.upseil.gdx.event.EventHandler;
import com.upseil.gdx.event.EventType;
import com.upseil.gdx.pool.pair.IntPairPool;
import com.upseil.gdx.pool.pair.PooledIntPair;
import com.upseil.gdx.util.GDXCollections;

public class EventSystem extends BaseEntitySystem implements RequiresStepping {
	
	private ComponentMapper<EventComponent> mapper;
	
	private final IntPairPool<EventComponent> pairPool;
	
	private final ObjectMap<EventType<?>, ObjectSet<EventHandler<?>>> handlers; 
    private final Array<PooledIntPair<EventComponent>> events; 
    private final IntArray eventsToDelete;

	public EventSystem() {
		super(Aspect.all(EventComponent.class));
		pairPool = new IntPairPool<>();
		handlers = new ObjectMap<>();
        events = new Array<>(false, 32, EventComponent.class);
        eventsToDelete = new IntArray(32);
	}
	
	@Override
	protected void inserted(int entityId) {
        EventComponent event = mapper.get(entityId);
        if (event.isFireImmediate()) {
            forwardToHandlers(entityId, event);
        } else {
            events.add(pairPool.obtain().set(entityId, event));
        }
	}
	
	@Override
	public void removed(IntBag entities) { }

	@Override
	protected void processSystem() {
	    GDXCollections.<PooledIntPair<EventComponent>>forEach(events, this::forwardToHandlers);
	    events.clear();
	    GDXCollections.forEach(eventsToDelete, world::delete);
	    eventsToDelete.clear();
	}

    private void forwardToHandlers(PooledIntPair<EventComponent> eventWithId) {
        forwardToHandlers(eventWithId.getA(), eventWithId.getB());
        pairPool.free(eventWithId);
    }

    private void forwardToHandlers(int entityId, EventComponent event) {
        if (event.get() == null) return; // Already deleted
        
        for (EventHandler<?> handler : handlers.get(event.getType())) {
            forwardEvent(event, handler);
        }
        eventsToDelete.add(entityId);
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
    
    public static EventComponent fire(World world, Event<?> event) {
        EventComponent eventComponent = world.createEntity().edit().create(EventComponent.class);
        eventComponent.set(event);
        return eventComponent;
    }
    
    public static EventComponent fireImmediate(World world, Event<?> event) {
        EventComponent eventComponent = world.createEntity().edit().create(EventComponent.class);
        eventComponent.set(event);
        eventComponent.setFireImmediate(true);
        return eventComponent;
    }

}
