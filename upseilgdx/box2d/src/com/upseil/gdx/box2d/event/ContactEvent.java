package com.upseil.gdx.box2d.event;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.upseil.gdx.pool.Pooled;

public interface ContactEvent<E extends ContactEvent<E>> extends Pooled<E> {

    int getSelfId();
    Fixture getSelfFixture();

    int getOtherId();
    Fixture getOtherFixture();
    
    Contact getContact();
    
    default WorldManifold getManifold() {
        return getContact().getWorldManifold();
    }
    
}
