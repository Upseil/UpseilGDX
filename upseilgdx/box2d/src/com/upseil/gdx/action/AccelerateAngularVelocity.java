package com.upseil.gdx.action;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

public class AccelerateAngularVelocity extends AbstractAction<Body, AccelerateAngularVelocity> {
    
    private float acceleration;
    private float targetVelocity;
    private boolean holdVelocity;

    @Override
    public boolean act(float deltaTime) {
        float currentVelocity = getState().getAngularVelocity();
        if (MathUtils.isEqual(currentVelocity, targetVelocity)) return !holdVelocity;
        
        float direction = Math.signum(targetVelocity - currentVelocity);
        float desiredVelocity = currentVelocity + direction * acceleration * deltaTime;
        if ((direction < 0 && desiredVelocity < targetVelocity) || (direction > 0 && desiredVelocity > targetVelocity)) {
            desiredVelocity = targetVelocity;
        }
        
        float inertia = getState().getInertia();
        float impulse = inertia * (desiredVelocity - currentVelocity);
        getState().applyAngularImpulse(impulse, true);
        return false;
    }
    
    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public float getTargetVelocity() {
        return targetVelocity;
    }

    public void setTargetVelocity(float targetVelocity) {
        this.targetVelocity = targetVelocity;
    }

    public boolean holdVelocity() {
        return holdVelocity;
    }

    public void setHoldVelocity(boolean holdVelocity) {
        this.holdVelocity = holdVelocity;
    }

    @Override
    public void reset() {
        super.reset();
        acceleration = 0;
        targetVelocity = 0;
        holdVelocity = false;
    }
    
}
