package com.upseil.gdx.util.condition;

import java.util.function.BooleanSupplier;

public interface Condition extends BooleanSupplier {
    
    Condition None = new NoCondition();
    
    boolean needsEvaluation();
    void evaluate();
    void invalidate();
    
    static Condition simple(BooleanSupplier simpleCondition) {
        return new SimpleCondition(simpleCondition);
    }
    static Condition not(Condition condition) {
        return new NegateCondition(condition);
    }
    static Condition and(Condition... conditions) {
        return new AndConditions(conditions);
    }
    static Condition or(Condition... conditions) {
        return new OrConditions(conditions);
    }
    
}
