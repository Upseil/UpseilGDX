package com.upseil.gdx.artemis.system;

import com.artemis.Aspect;
import com.artemis.World;

public class AllSubscriptionMisplacementWorkaround extends PassiveSystem {
    
    public AllSubscriptionMisplacementWorkaround() {
    }
    
    @Override
    protected void setWorld(World world) {
        super.setWorld(world);
        world.getAspectSubscriptionManager().get(Aspect.all());
    }
    
}
