package com.upseil.gdx.box2d.action;

import com.upseil.gdx.pool.AbstractPooled;

public abstract class AbstractPhysicsAction<T extends AbstractPhysicsAction<T>> extends AbstractPooled<T> implements PhysicsAction<T> {

}
