package com.upseil.gdx.artemis.system.invocation;

import com.artemis.InvocationStrategy;

public class PausableInvocationStrategy extends InvocationStrategy {
    
    private boolean paused;
    private float stepTime;

    @Override
    protected void process() {
        if (paused) {
            if (stepTime > 0) {
                stepTime -= world.delta;
            } else {
                world.delta = 0;
                stepTime = 0;
            }
        }
        super.process();
    }
    
    public void setPaused(boolean isPaused) {
        this.paused = isPaused;
        stepTime = 0;
    }
    
    public boolean isPaused() {
        return paused;
    }
    
    public void step(float time) {
        stepTime += time;
    }
    
}
