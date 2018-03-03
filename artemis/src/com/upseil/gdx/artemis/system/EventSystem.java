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
import com.upseil.gdx.artemis.event.ResizeEvent;
import com.upseil.gdx.event.Event;
import com.upseil.gdx.event.EventHandler;
import com.upseil.gdx.event.EventType;
import com.upseil.gdx.pool.PooledPools;
import com.upseil.gdx.pool.pair.IntPairPool;
import com.upseil.gdx.pool.pair.PooledIntPair;
import com.upseil.gdx.util.GDXCollections;
import com.upseil.gdx.util.RequiresResize;

public class EventSystem extends BaseEntitySystem implements RequiresStepping, RequiresResize {
    
	private ComponentMapper<EventComponent> mapper;
	
	private final IntPairPool<EventComponent> pairPool;
	
	private final ObjectMap<EventType<?>, ObjectSet<EventHandler<?>>> handlers; 
    private final Array<PooledIntPair<EventComponent>> events; 
    private final IntArray eventsToDelete;

	public EventSystem() {
		super(Aspect.all(EventComponent.class));
		pairPool = new IntPairPool<>();
		handlers = new ObjectMap<>();
        events = new Array<>(false, 32);
        eventsToDelete = new IntArray(32);
	}
	
	@Override
	protected void inserted(int entityId) {
        EventComponent event = mapper.get(entityId);
        if (event.isImmediate()) {
            forwardToHandlers(entityId, event);
        } else {
            events.add(pairPool.obtain().set(entityId, event));
        }
	}
	
	@Override
	public void removed(IntBag entities) { }

	@Override
	protected void processSystem() {
	    for (PooledIntPair<EventComponent> eventWithId : events) {
            fireScheduledEvent(eventWithId);
        }
	    events.clear();
	    
	    int[] idsToDelete = eventsToDelete.items;
	    int eventsToDeleteSize = eventsToDelete.size;
	    for (int index = 0; index < eventsToDeleteSize; index++) {
	        world.delete(idsToDelete[index]);
	    }
	    eventsToDelete.clear();
	}

    private void fireScheduledEvent(PooledIntPair<EventComponent> eventWithId) {
        forwardToHandlers(eventWithId.getA(), eventWithId.getB());
        pairPool.free(eventWithId);
    }

    private void forwardToHandlers(int entityId, EventComponent event) {
        if (event.get() == null) return; // Already deleted
        forwardToHandlers(entityId, event.get());
    }

    private void forwardToHandlers(int entityId, Event<?> event) {
        for (EventHandler<?> handler : handlers.get(event.getType(), GDXCollections.emptySet())) {
            forwardEvent(event, handler);
        } 
        if (entityId != -1) eventsToDelete.add(entityId);
    }
    
    @SuppressWarnings("unchecked")
    private <T extends Event<T>> void forwardEvent(Event<?> event, EventHandler<T> handler) {
        handler.accept((T) event);
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
    
    @Override
    public void resize(int width, int height) {
        if (handlers.containsKey(ResizeEvent.Type)) {
            schedule(world, PooledPools.obtain(ResizeEvent.class).set(width, height));
        }
    }
    
    public static EventComponent schedule(World world, Event<?> event) {
        EventComponent eventComponent = world.createEntity().edit().create(EventComponent.class);
        eventComponent.set(event);
        return eventComponent;
    }
    
    public static EventComponent scheduleImmediate(World world, Event<?> event) {
        EventComponent eventComponent = world.createEntity().edit().create(EventComponent.class);
        eventComponent.set(event);
        eventComponent.setImmediate(true);
        return eventComponent;
    }
    
    public static void fire(World world, Event<?> event) {
        world.getSystem(EventSystem.class).forwardToHandlers(-1, event);
    }

}
