package com.upseil.gdx.scene2d;

public class ButtonClickedListener extends CheckedListener {
    
    private final Runnable action;
    
    public ButtonClickedListener(Runnable action) {
        this.action = action;
    }

    @Override
    protected void checkedChanged(boolean checked) {
        action.run();
    }
    
}
