package com.upseil.gdx.util.condition;

public abstract class AbstractCondition implements Condition {
    
    private boolean value;
    private boolean needsEvaluation;
    
    public AbstractCondition() {
        needsEvaluation = true;
    }
    
    @Override
    public boolean getAsBoolean() {
        if (needsEvaluation()) {
            evaluate();
        }
        return value;
    }

    @Override
    public boolean needsEvaluation() {
        return needsEvaluation;
    }

    @Override
    public void invalidate() {
        needsEvaluation = true;
    }
    
    @Override
    public void evaluate() {
        value = evaluateValue();
        needsEvaluation = false;
    }
    
    protected abstract boolean evaluateValue();
    
}
