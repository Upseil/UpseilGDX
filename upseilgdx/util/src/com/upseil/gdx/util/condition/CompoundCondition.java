package com.upseil.gdx.util.condition;

public abstract class CompoundCondition extends AbstractCondition {
    
    private final Condition[] conditions;
    protected boolean evaluationFinished;

    
    public CompoundCondition(Condition... conditions) {
        this.conditions = new Condition[conditions.length];
        for (int index = 0; index < conditions.length; index++) {
            this.conditions[index] = conditions[index];
        }
    }
    
    @Override
    public void invalidate() {
        super.invalidate();
        for (Condition condition : conditions) {
            condition.invalidate();
        }
    }
    
    @Override
    protected boolean evaluateValue() {
        evaluationFinished = false;
        boolean value = conditions[0].getAsBoolean();
        for (int index = 1; index < conditions.length; index++) {
            Condition condition = conditions[index];
            value = combine(value, condition.getAsBoolean());
            if (evaluationFinished) {
                break;
            }
        }
        return value;
    }

    protected abstract boolean combine(boolean first, boolean second);
    
}
