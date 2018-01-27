package com.upseil.gdx.artemis.system.invocation;

import com.artemis.BaseSystem;

public class SystemHandle {
    
    final BaseSystem system;
    final int disabledIndex;
    
    public SystemHandle(BaseSystem system, int disabledIndex) {
        this.system = system;
        this.disabledIndex = disabledIndex;
    }

    public BaseSystem getSystem() {
        return system;
    }

    public int getDisabledIndex() {
        return disabledIndex;
    }
    
}