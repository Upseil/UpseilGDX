package com.upseil.gdx.artemis.component;

import com.artemis.PooledComponent;

public class Name extends PooledComponent {
    
    private String name;
    
    public String get() {
        return name;
    }

    public void set(String name) {
        this.name = name;
    }

    @Override
    protected void reset() {
        name = null;
    }
    
}
