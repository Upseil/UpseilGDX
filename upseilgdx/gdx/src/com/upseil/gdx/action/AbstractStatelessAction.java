package com.upseil.gdx.action;

public abstract class AbstractStatelessAction<T extends AbstractStatelessAction<T>> extends AbstractAction<Void, T> implements StatelessAction<T> {
    
}
