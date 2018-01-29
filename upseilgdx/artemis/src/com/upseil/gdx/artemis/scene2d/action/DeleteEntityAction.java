package com.upseil.gdx.artemis.scene2d.action;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.scenes.scene2d.Action;

public class DeleteEntityAction extends Action {
    
    private Entity entity;
    
    private boolean deleted;
    
    public DeleteEntityAction setEntity(Entity entity) {
        this.entity = entity;
        return this;
    }
    
    public DeleteEntityAction setEntity(World world, int entityId) {
        this.entity = world.getEntity(entityId);
        return this;
    }
    
    @Override
    public boolean act(float delta) {
        if (!deleted) {
            entity.deleteFromWorld();
            deleted = true;
        }
        return true;
    }
    
    @Override
    public void restart() {
        deleted = false;
    }
    
    @Override
    public void reset() {
        super.reset();
        entity = null;
    }
    
}
