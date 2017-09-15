package com.upseil.gdx.util.condition;

public class NoCondition implements Condition {
    
    @Override
    public boolean getAsBoolean() {
        return true;
    }
    
    @Override
    public boolean needsEvaluation() {
        return false;
    }
    
    @Override
    public void evaluate() {
    }
    
    @Override
    public void invalidate() {
    }
    
}
