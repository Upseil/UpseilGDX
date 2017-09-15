package com.upseil.gdx.util.condition;

import java.util.function.BooleanSupplier;

public class SimpleCondition extends AbstractCondition {
    
    private final BooleanSupplier supplier;

    public SimpleCondition(BooleanSupplier supplier) {
        this.supplier = supplier;
    }

    @Override
    protected boolean evaluateValue() {
        return supplier.getAsBoolean();
    }
    
}
