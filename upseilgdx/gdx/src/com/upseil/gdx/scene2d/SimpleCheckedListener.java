package com.upseil.gdx.scene2d;

import com.upseil.gdx.util.function.BooleanConsumer;

public class SimpleCheckedListener extends CheckedListener {
    
    private final BooleanConsumer action;
    
    public SimpleCheckedListener(BooleanConsumer action) {
        this.action = action;
    }

    @Override
    protected void checkedChanged(boolean checked) {
        action.accept(checked);
    }
    
}
