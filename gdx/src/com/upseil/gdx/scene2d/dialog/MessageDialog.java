package com.upseil.gdx.scene2d.dialog;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

public class MessageDialog extends AbstractDialog {
    
    protected static final int MinButtonWidth = 75;
    
    private final Label messageLabel;
    
    public MessageDialog(String message, String buttonText, Skin skin) {
        this("", message, buttonText, skin);
    }

    public MessageDialog(String title, String message, String buttonText, Skin skin) {
        super(title, skin, (title.isEmpty() ? "titleless-" : "") + "modal-dialog", true);
        setMovable(false);
        
        messageLabel = new Label(message, skin);
        if (message != null) {
            messageLabel.setAlignment(Align.center);
            float labelWidth = messageLabel.getPrefWidth();
            messageLabel.setWrap(true);
            text(messageLabel);
            getMessageLabelCell().width(labelWidth);
        }
        
        button(buttonText, MinButtonWidth, null);
        key(Keys.ESCAPE, null);
        key(Keys.ENTER, null);
    }
    
    public Label getMessageLabel() {
        return messageLabel;
    }
    
    public Cell<Label> getMessageLabelCell() {
        return getContentTable().getCell(messageLabel);
    }
    
}
