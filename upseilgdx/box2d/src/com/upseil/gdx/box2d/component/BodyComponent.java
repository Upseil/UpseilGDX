package com.upseil.gdx.box2d.component;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class BodyComponent extends PooledComponent {
    
    private Body body;
    
    public Body get() {
        return body;
    }

    public void set(Body body) {
        this.body = body;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }
    
    public float getRotation() {
        return body.getAngle() * MathUtils.radiansToDegrees;
    }

    @Override
    protected void reset() {
        body.getWorld().destroyBody(body);
        body = null;
    }
    
}
