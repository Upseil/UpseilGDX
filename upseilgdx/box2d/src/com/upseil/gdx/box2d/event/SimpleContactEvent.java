package com.upseil.gdx.box2d.event;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

public class SimpleContactEvent extends AbstractContactEvent<SimpleContactEvent> {
    
    public SimpleContactEvent set(int selfId, Fixture selfFixture, int otherId, Fixture otherFixture, Contact contact) {
        super.intialize(selfId, selfFixture, otherId, otherFixture, contact);
        return this;
    }
    
}
