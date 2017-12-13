package com.upseil.gdx.action;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class AccelerateToVelocity extends AbstractAction<Body, AccelerateToVelocity> {

    private float acceleration;
    private Vector2 targetVelocity;
    private Vector2 desiredVelocity;
    
    public AccelerateToVelocity() {
        targetVelocity = new Vector2();
        desiredVelocity = new Vector2();
    }

    @Override
    public boolean act(float deltaTime) {
        Vector2 currentVelocity = getState().getLinearVelocity();
        if (targetVelocity.epsilonEquals(currentVelocity, MathUtils.FLOAT_ROUNDING_ERROR)) return true;
        
        float xDirection = Math.signum(targetVelocity.x - currentVelocity.x);
        float yDirection = Math.signum(targetVelocity.y - currentVelocity.y);
//        desiredVelocity.x = MathUtils.clamp(currentVelocity.x + xDirection * acceleration * deltaTime, -targetVelocity.x, targetVelocity.x);
//        desiredVelocity.y = MathUtils.clamp(currentVelocity.y + yDirection * acceleration * deltaTime, -targetVelocity.y, targetVelocity.y);
        desiredVelocity.x = Math.min(Math.abs(currentVelocity.x) + acceleration * deltaTime, Math.abs(targetVelocity.x)) * xDirection;
        desiredVelocity.y = Math.min(Math.abs(currentVelocity.y) + acceleration * deltaTime, Math.abs(targetVelocity.y)) * yDirection; 
        
        float mass = getState().getMass();
        float impulseX = mass * (desiredVelocity.x - currentVelocity.x);
        float impulseY = mass * (desiredVelocity.y - currentVelocity.y);
        
        Vector2 position = getState().getPosition();
        getState().applyLinearImpulse(impulseX, impulseY, position.x, position.y, true);
        
        return false;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
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
    
    public void setTargetVelocityX(int xVelocity) {
        targetVelocity.x = xVelocity;
    }
    
    public void setTargetVelocityY(int yVelocity) {
        targetVelocity.y = yVelocity;
    }

    @Override
    public void reset() {
        super.reset();
        acceleration = Float.MAX_VALUE;
        targetVelocity.set(0, 0);
        desiredVelocity.set(0, 0);
    }
    
}
