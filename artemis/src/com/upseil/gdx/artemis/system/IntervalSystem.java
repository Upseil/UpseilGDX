package com.upseil.gdx.artemis.system;

import com.artemis.BaseSystem;

public abstract class IntervalSystem extends BaseSystem {

    private float interval;
    private float accumulatedDelta;

    public IntervalSystem(float interval) {
        this.interval = interval;
    }
    
    @Override
    protected void initialize() {
        setEnabled(interval > 0);
    }

    @Override
    protected boolean checkProcessing() {
        accumulatedDelta += world.getDelta();
        if(accumulatedDelta >= interval) {
            accumulatedDelta -= interval;
            return true;
        }
        return false;
    }
    
    protected void resetDelta() {
        accumulatedDelta = 0;
    }

    protected void setDelta(float delta) {
        accumulatedDelta = delta;
    }
    
    public float getInterval() {
        return interval;
    }

    protected void setInterval(float interval) {
        this.interval = interval;
        setEnabled(interval > 0);
    }
    
}
