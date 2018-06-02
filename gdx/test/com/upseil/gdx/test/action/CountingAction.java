package com.upseil.gdx.test.action;

import com.upseil.gdx.action.AbstractAction;

public class CountingAction extends AbstractAction<Integer, CountingAction> {
    
    private int startValue;
    private int step;
    private Integer endValue;
    
    public void set(int startValue, int step) {
        set(startValue, step, null);
    }

    public void set(int startValue, int step, Integer endValue) {
        this.startValue = startValue;
        this.step = step;
        this.endValue = endValue;
        restart();
    }

    @Override
    public boolean act(float deltaTime) {
        setState(getState() + step);
        return endValue != null && getState() >= endValue;
    }
    
    @Override
    public void restart() {
        setState(startValue);
    }
    
    @Override
    public void reset() {
        super.reset();
        startValue = 0;
        step = 0;
        endValue = null;
    }
    
}