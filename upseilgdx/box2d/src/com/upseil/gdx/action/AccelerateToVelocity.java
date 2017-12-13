package com.upseil.gdx.action;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class AccelerateToVelocity extends AbstractAction<Body, AccelerateToVelocity> {

    private Vector2 acceleration;
    private Vector2 targetVelocity;
    private boolean holdVelocity;
    
    public AccelerateToVelocity() {
        acceleration = new Vector2();
        targetVelocity = new Vector2();
    }

    @Override
    public boolean act(float deltaTime) {
        Vector2 currentVelocity = getState().getLinearVelocity();
        if (targetVelocity.epsilonEquals(currentVelocity, MathUtils.FLOAT_ROUNDING_ERROR)) return !holdVelocity;

        float xDirection = Math.signum(targetVelocity.x - currentVelocity.x);
        float desiredXVelocity = currentVelocity.x + xDirection * acceleration.x * deltaTime;
        if ((xDirection < 0 && desiredXVelocity < targetVelocity.x) || (xDirection > 0 && desiredXVelocity > targetVelocity.x)) {
            desiredXVelocity = targetVelocity.x;
        }
        
        float yDirection = Math.signum(targetVelocity.y - currentVelocity.y);
        float desiredYVelocity = currentVelocity.y + yDirection * acceleration.y * deltaTime;
        if ((yDirection < 0 && desiredYVelocity < targetVelocity.y) || (yDirection > 0 && desiredYVelocity > targetVelocity.y)) {
            desiredYVelocity = targetVelocity.y;
        }
        
        float mass = getState().getMass();
        float impulseX = mass * (desiredXVelocity - currentVelocity.x);
        float impulseY = mass * (desiredYVelocity - currentVelocity.y);
        
        Vector2 position = getState().getPosition();
        getState().applyLinearImpulse(impulseX, impulseY, position.x, position.y, true);
        
        return false;
    }
    
    public Vector2 getAcceleration() {
        return acceleration;
    }
    
    public void setAcceleration(Vector2 acceleration) {
        setAcceleration(acceleration.x, acceleration.y);
    }

    public void setAcceleration(float xAcceleration, float yAcceleration) {
        acceleration.set(xAcceleration, yAcceleration);
    }
    
    public void setAccelerationX(float xAcceleration) {
        acceleration.x = xAcceleration;
    }
    
    public void setAccelerationY(float yAcceleration) {
        acceleration.y = yAcceleration;
    }
    
    public Vector2 getTargetVelocity() {
        return targetVelocity;
    }
    
    public void setTargetVelocity(Vector2 targetVelocity) {
        this.targetVelocity.set(targetVelocity);
    }

    public void setTargetVelocity(float xVelocity, float yVelocity) {
        targetVelocity.set(xVelocity, yVelocity);
    }
    
    public void setTargetVelocityX(float xVelocity) {
        targetVelocity.x = xVelocity;
    }
    
    public void setTargetVelocityY(float yVelocity) {
        targetVelocity.y = yVelocity;
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
        acceleration.set(0, 0);
        targetVelocity.set(0, 0);
        holdVelocity = false;
    }
    
}
