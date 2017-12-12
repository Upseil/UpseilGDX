package com.upseil.gdx.box2d.event;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;

public class PostSolveEvent extends AbstractContactEvent<PostSolveEvent> {
    
    private ContactImpulse impulse;
    
    public PostSolveEvent set(int selfId, Fixture selfFixture, int otherId, Fixture otherFixture, Contact contact, ContactImpulse impulse) {
        super.intialize(selfId, selfFixture, otherId, otherFixture, contact);
        this.impulse = impulse;
        return this;
    }

    public ContactImpulse getImpulse() {
        return impulse;
    }
    
    @Override
    public void reset() {
        super.reset();
        impulse = null;
    }
    
}
