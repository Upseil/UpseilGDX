package com.upseil.gdx.action;

import com.upseil.gdx.pool.AbstractPooled;

public abstract class AbstractAction<T extends AbstractAction<T>> extends AbstractPooled<T> implements Action<T> {

}
