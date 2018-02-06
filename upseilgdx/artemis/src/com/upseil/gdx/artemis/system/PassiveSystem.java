package com.upseil.gdx.artemis.system;

import com.artemis.BaseSystem;

public abstract class PassiveSystem extends BaseSystem {
    
    @Override
    protected boolean checkProcessing() {
        return false;
    }
    
    @Override
    protected void processSystem() { }
    
}
