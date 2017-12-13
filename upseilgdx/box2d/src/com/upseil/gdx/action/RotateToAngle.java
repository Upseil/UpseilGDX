package com.upseil.gdx.action;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

public class RotateToAngle extends AbstractAction<Body, RotateToAngle> {
    
    private float targetAngle;
    private float velocityLimit;

    @Override
    public boolean act(float deltaTime) {
        float currentAngle = getState().getAngle();
        if (MathUtils.isEqual(targetAngle, currentAngle)) return true;
        
        float currentVelocity = getState().getAngularVelocity();
        float nextAngle = currentAngle + currentVelocity * deltaTime;
        float angleToRotate = targetAngle - nextAngle;
        while (angleToRotate < -MathUtils.PI) angleToRotate += MathUtils.PI2;
        while (angleToRotate >  MathUtils.PI) angleToRotate -= MathUtils.PI2;
        
        float desiredVelocity = MathUtils.clamp(angleToRotate / deltaTime, -velocityLimit, velocityLimit);
        float impulse = getState().getInertia() * desiredVelocity;
        getState().applyAngularImpulse(impulse, true);
        return false;
    }

    public float getTargetAngleInRadian() {
        return targetAngle;
    }
    
    public float getTargetAngleInDegree() {
        return targetAngle * MathUtils.radiansToDegrees;
    }

    public void setTargetAngleInRadian(float targetAngle) {
        this.targetAngle = targetAngle;
    }
    
    public void setTargetAngleInDegree(float targetAngle) {
        this.targetAngle = targetAngle * MathUtils.degreesToRadians;
    }
    
    public float getVelocityLimit() {
        return velocityLimit;
    }
    
    public void setVelocityLimit(float velocityLimit) {
        this.velocityLimit = velocityLimit;
    }
    
    @Override
    public void reset() {
        super.reset();
        targetAngle = 0;
        velocityLimit = Float.MAX_VALUE;
    }
    
}
