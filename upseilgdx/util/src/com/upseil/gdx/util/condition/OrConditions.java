package com.upseil.gdx.util.condition;

public class OrConditions extends CompoundCondition {
    
    public OrConditions() {
        super();
    }

    public OrConditions(Condition... conditions) {
        super(conditions);
    }

    @Override
    protected boolean combine(boolean first, boolean second) {
        evaluationFinished = first || second;
        return evaluationFinished;
    }
    
}
