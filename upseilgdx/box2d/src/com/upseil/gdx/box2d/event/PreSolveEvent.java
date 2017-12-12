package com.upseil.gdx.box2d.event;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class PreSolveEvent extends AbstractContactEvent<PreSolveEvent> {
    
    private Manifold oldManifold;
    
    public PreSolveEvent set(int selfId, Fixture selfFixture, int otherId, Fixture otherFixture, Contact contact, Manifold oldManifold) {
        super.intialize(selfId, selfFixture, otherId, otherFixture, contact);
        this.oldManifold = oldManifold;
        return this;
    }
    
    public Manifold getOldManifold() {
        return oldManifold;
    }
    
    @Override
    public void reset() {
        super.reset();
        oldManifold = null;
    }
    
}
