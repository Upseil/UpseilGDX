package com.upseil.gdx.action;

public class InfiniteAction<S, A extends Action<S, A>> extends DelegateAction<S, A, InfiniteAction<S, A>> {
    
    @Override
    protected boolean delegate(float deltaTime) {
        if (getAction() != null) getAction().act(deltaTime);
        return false;
    }
    
}
