package com.upseil.gdx.box2d.event;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.upseil.gdx.pool.AbstractPooled;

public abstract class AbstractContactEvent<E extends AbstractContactEvent<E>> extends AbstractPooled<E> implements ContactEvent<E> {
    
    private int selfId;
    private Fixture selfFixture;
    
    private int otherId;
    private Fixture otherFixture;
    
    private Contact contact;
    
    protected void intialize(int selfId, Fixture selfFixture, int otherId, Fixture otherFixture, Contact contact) {
        this.selfId = selfId;
        this.selfFixture = selfFixture;
        this.otherId = otherId;
        this.otherFixture = otherFixture;
        this.contact = contact;
    }

    public int getSelfId() {
        return selfId;
    }

    public Fixture getSelfFixture() {
        return selfFixture;
    }

    public int getOtherId() {
        return otherId;
    }

    public Fixture getOtherFixture() {
        return otherFixture;
    }
    
    public Contact getContact() {
        return contact;
    }

    @Override
    public void reset() {
        super.reset();
        selfId = -1;
        selfFixture = null;
        otherId = -1;
        otherFixture = null;
        contact = null;
    }
    
}
