package com.upseil.gdx.box2d.action;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.upseil.gdx.action.AbstractAction;

public class RotateToAngle extends AbstractAction<Body, RotateToAngle> {
    
    private static final float DefaultLookAheadSteps = 6;
    
    private float targetAngle;
    private float velocityLimit;
    private float lookAheadTime;

    @Override
    public boolean act(float deltaTime) {
        float currentAngle = getState().getAngle();
        float currentVelocity = getState().getAngularVelocity();
        if (MathUtils.isEqual(targetAngle, currentAngle) && MathUtils.isZero(currentVelocity)) return true;
        
        if (lookAheadTime < 0) lookAheadTime = deltaTime * DefaultLookAheadSteps;
        
        float nextAngle = currentAngle + currentVelocity * lookAheadTime;
        float angleToRotate = targetAngle - nextAngle;
        while (angleToRotate < -MathUtils.PI) angleToRotate += MathUtils.PI2;
        while (angleToRotate >  MathUtils.PI) angleToRotate -= MathUtils.PI2;
        
        float desiredVelocity = angleToRotate / lookAheadTime;
        desiredVelocity = Math.min(velocityLimit, Math.max(desiredVelocity, -velocityLimit));
        
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
    
    public float getVelocityLimitInRadian() {
        return velocityLimit;
    }
    
    public float getVelocityLimitInDegree() {
        return velocityLimit * MathUtils.radiansToDegrees;
    }
    
    public void setVelocityLimitInRadian(float velocityLimit) {
        this.velocityLimit = velocityLimit;
    }
    
    public void setVelocityLimitInDegree(float velocityLimit) {
        this.velocityLimit = velocityLimit * MathUtils.degreesToRadians;
    }
    
    public float getLookAheadTime() {
        return lookAheadTime;
    }

    public void setLookAheadTime(float lookAheadTime) {
        this.lookAheadTime = lookAheadTime;
    }

    @Override
    public void reset() {
        super.reset();
        targetAngle = 0;
        velocityLimit = MathUtils.PI;
        lookAheadTime = -1;
    }
    
}
