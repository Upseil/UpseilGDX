package com.upseil.gdx.util.condition;

public class AndConditions extends CompoundCondition {
    
    public AndConditions() {
        super();
    }

    public AndConditions(Condition... conditions) {
        super(conditions);
    }

    @Override
    protected boolean combine(boolean first, boolean second) {
        evaluationFinished = !first || !second;
        return !evaluationFinished;
    }
    
}
