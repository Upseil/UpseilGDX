package com.upseil.gdx.util.condition;

public class NegateCondition implements Condition {
    
    private final Condition condition;

    public NegateCondition(Condition condition) {
        this.condition = condition;
    }
    
    @Override
    public boolean getAsBoolean() {
        return !condition.getAsBoolean();
    }
    
    @Override
    public boolean needsEvaluation() {
        return condition.needsEvaluation();
    }
    
    @Override
    public void evaluate() {
        condition.evaluate();
    }
    
    @Override
    public void invalidate() {
        condition.invalidate();
    }
    
}
