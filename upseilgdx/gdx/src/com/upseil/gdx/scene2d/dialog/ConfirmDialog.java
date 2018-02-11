package com.upseil.gdx.scene2d.dialog;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ConfirmDialog extends AbstractDialog {
    
    private final Label messageLabel;
    private final Runnable action;

    public ConfirmDialog(String title, String message, String cancelText, String confirmText, Skin skin, Runnable action) {
        super(title, skin, true);
        setMovable(false);
        this.action = action;
        
        messageLabel = new Label(message, skin);
        if (message != null) {
            text(messageLabel);
        }
        
        button(cancelText);
        key(Keys.ESCAPE, null);
        button(confirmText, notNull);
        key(Keys.ENTER, notNull);
    }
    
    public Label getMessageLabel() {
        return messageLabel;
    }
    
    public Cell<Label> getMessageLabelCell() {
        return getContentTable().getCell(messageLabel);
    }
    
    @Override
    protected void result(Object object) {
        if (object != null) {
            action.run();
        }
    }
    
}
