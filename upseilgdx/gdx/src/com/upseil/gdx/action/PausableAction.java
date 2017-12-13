package com.upseil.gdx.action;

public class PausableAction<S, A extends Action<S, A>> extends DelegateAction<S, A, PausableAction<S, A>> {
    
    private boolean isActive;
    
    @Override
    public boolean delegate(float deltaTime) {
        if (isActive && getAction() != null) return getAction().act(deltaTime);
        return false;
    }
    
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public void reset() {
        super.reset();
        isActive = true;
    }
    
}
