package com.upseil.gdx.scene2d.dialog;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ConfirmDialog extends MessageDialog {
    
    private final Runnable action;

    public ConfirmDialog(String message, String cancelText, String confirmText, Skin skin, Runnable action) {
        this("", message, cancelText, confirmText, skin, action);
    }

    public ConfirmDialog(String title, String message, String cancelText, String confirmText, Skin skin, Runnable action) {
        super(title, message, cancelText, skin);
        setMovable(false);
        this.action = action;
        
        key(Keys.ESCAPE, null);
        button(confirmText, MinButtonWidth, notNull);
        key(Keys.ENTER, notNull);
    }
    
    @Override
    protected void result(Object object) {
        if (object != null) {
            action.run();
        }
    }
    
}
