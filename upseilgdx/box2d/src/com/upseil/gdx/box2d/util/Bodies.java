package com.upseil.gdx.box2d.util;

import com.badlogic.gdx.physics.box2d.BodyDef;

public final class Bodies {
    
    public static final BodyDef DefaultBodyDefinition = new BodyDef();

    public static BodyDef copy(BodyDef bodyDefinition) {
        BodyDef clone = new BodyDef();
        apply(bodyDefinition, clone);
        return clone;
    }

    public static void apply(BodyDef source, BodyDef target) {
        target.type = source.type;
        target.position.set(source.position);
        target.angle = source.angle;
        target.linearVelocity.set(source.linearVelocity);
        target.angularVelocity = source.angularVelocity;
        target.linearDamping = source.linearDamping;
        target.angularDamping = source.angularDamping;
        target.allowSleep = source.allowSleep;
        target.awake = source.awake;
        target.fixedRotation = source.fixedRotation;
        target.bullet = source.bullet;
        target.active = source.active;
        target.gravityScale = source.gravityScale;
    }
    
    private Bodies() { }
    
}
