package com.upseil.gdx.artemis.component;

import com.artemis.PooledComponent;

public class Layer extends PooledComponent {
    
    private int zIndex;

    public int getZIndex() {
        return zIndex;
    }

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    @Override
    protected void reset() {
        zIndex = 0;
    }
    
}
