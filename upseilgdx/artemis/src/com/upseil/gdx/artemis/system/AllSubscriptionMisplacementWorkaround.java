package com.upseil.gdx.artemis.system;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;

public class AllSubscriptionMisplacementWorkaround extends BaseEntitySystem {
    
    public AllSubscriptionMisplacementWorkaround() {
        super(Aspect.all());
    }
    
    @Override
    protected boolean checkProcessing() {
        return false;
    }

    @Override
    protected void processSystem() { }
    
}
