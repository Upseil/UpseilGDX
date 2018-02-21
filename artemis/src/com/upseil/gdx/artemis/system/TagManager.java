package com.upseil.gdx.artemis.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySubscription.SubscriptionListener;
import com.artemis.annotations.SkipWire;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectIntMap;

@SkipWire // Generic typing seems to break wiring in GWT
public class TagManager<TagType> extends PassiveSystem {
    
    private final ObjectIntMap<String> entitiesByTag;
    private final IntMap<String> tagsByEntity;
    
    public TagManager() {
        entitiesByTag = new ObjectIntMap<>();
        tagsByEntity = new IntMap<>();
    }
    
    @Override
    protected void initialize() {
        world.getAspectSubscriptionManager().get(Aspect.all()).addSubscriptionListener(new SubscriptionListener() {
            @Override
            public void removed(IntBag entities) {
                int[] ids = entities.getData();
                for (int i = 0; i < entities.size(); i++) {
                    int id = ids[i];
                    if (tagsByEntity.containsKey(id)) {
                        String removedTag = tagsByEntity.remove(id);
                        entitiesByTag.remove(removedTag, -1);
                    }
                }
            }
            @Override
            public void inserted(IntBag entities) { }
        });
    }
    
    public void register(TagType tag, Entity entity) {
        register(tag, entity.getId());
    }
    
    public void register(TagType tag, int entity) {
        register(tag.toString(), entity);
    }
    
    public void register(String tag, Entity entity) {
        register(tag, entity.getId());
    }
    
    public void register(String tag, int entity) {
        unregister(tag);
        String entityTag = getTag(entity);
        if (entityTag != null) {
            unregister(entityTag);
        }
        
        entitiesByTag.put(tag, entity);
        tagsByEntity.put(entity, tag);
    }
    
    public int unregister(TagType tag) {
        return unregister(tag.toString());
    }
    
    public int unregister(String tag) {
        int removed = entitiesByTag.get(tag, -1);
        if (removed != -1) {
            tagsByEntity.remove(removed);
        }
        return -1;
    }
    
    public boolean isRegistered(TagType tag) {
        return isRegistered(tag.toString());
    }
    
    public boolean isRegistered(String tag) {
        return entitiesByTag.containsKey(tag);
    }

    public Entity getEntity(TagType tag) {
        return getEntity(tag.toString());
    }
    
    public Entity getEntity(String tag) {
        return world.getEntity(getEntityId(tag));
    }
    
    public int getEntityId(TagType tag) {
        return getEntityId(tag.toString());
    }
    
    public int getEntityId(String tag) {
        return entitiesByTag.get(tag, -1);
    }
    
    public String getTag(int entity) {
        return tagsByEntity.get(entity);
    }
    
    public Array<String> getTags() {
        return entitiesByTag.keys().toArray();
    }
    
}
