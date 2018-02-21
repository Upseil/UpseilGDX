package com.upseil.gdx.artemis.system;

import com.artemis.Aspect.Builder;

public abstract class LayeredIteratingSystem extends LayeredEntitySystem {
    
    public LayeredIteratingSystem(Builder aspect) {
        super(aspect);
    }

    @Override
    protected void processSystem() {
        EntityIterator iterator = iterator();
        while (iterator.hasNext()) {
            process(iterator.next());
        }
    }

    protected abstract void process(int entityId);
    
}
