package com.upseil.gdx.box2d.action;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.upseil.gdx.action.AbstractAction;

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
    
    public float getAccelerationInRadian() {
        return acceleration;
    }
    
    public float getAccelerationInDegree() {
        return acceleration * MathUtils.radiansToDegrees;
    }

    public void setAccelerationInRadian(float acceleration) {
        this.acceleration = acceleration;
    }

    public void setAccelerationInDegree(float acceleration) {
        this.acceleration = acceleration * MathUtils.degreesToRadians;
    }

    public float getTargetVelocityInRadian() {
        return targetVelocity;
    }

    public float getTargetVelocityInDegree() {
        return targetVelocity * MathUtils.radiansToDegrees;
    }

    public void setTargetVelocityInRadian(float targetVelocity) {
        this.targetVelocity = targetVelocity;
    }

    public void setTargetVelocityInDegree(float targetVelocity) {
        this.targetVelocity = targetVelocity * MathUtils.degreesToRadians;
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
