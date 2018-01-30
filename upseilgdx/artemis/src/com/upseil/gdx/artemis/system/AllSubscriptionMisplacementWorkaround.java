package com.upseil.gdx.artemis.system;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.World;

public class AllSubscriptionMisplacementWorkaround extends BaseSystem {
    
    public AllSubscriptionMisplacementWorkaround() {
    }
    
    @Override
    protected void setWorld(World world) {
        super.setWorld(world);
        world.getAspectSubscriptionManager().get(Aspect.all());
    }
    
    @Override
    protected boolean checkProcessing() {
        return false;
    }

    @Override
    protected void processSystem() { }
    
}
