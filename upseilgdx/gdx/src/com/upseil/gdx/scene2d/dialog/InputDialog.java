package com.upseil.gdx.scene2d.dialog;

import java.util.function.Consumer;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public abstract class InputDialog<T> extends AbstractDialog {
    
    protected static final int MinButtonWidth = 75;

    private final TextButton confirmButton;
    private final Consumer<T> action;

    public InputDialog(String title, String cancelText, String confirmText, Skin skin, Consumer<T> action) {
        super(title, skin, true);
        setMovable(false);
        this.action = action;
        
        confirmButton = new TextButton(confirmText, skin);
        confirmButton.setDisabled(true);
        
        button(cancelText, MinButtonWidth, null);
        key(Keys.ESCAPE, null);
        button(confirmButton, MinButtonWidth, notNull);
        key(Keys.ENTER, notNull);
    }
    
    protected void setValid(boolean isValid) {
        confirmButton.setDisabled(!isValid);
    }
    
    @Override
    protected void result(Object object) {
        if (object != null) {
            action.accept(getResult());
        }
    }

    protected abstract T getResult();
    
}
