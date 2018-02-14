package com.upseil.gdx.scene2d.dialog;

import java.util.function.Consumer;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.upseil.gdx.util.function.BooleanFunction;

public class LargeTextInputDialog extends AbstractTextInputDialog {

    public LargeTextInputDialog(String title, String message, String cancelText, String confirmText, Skin skin, Consumer<String> action) {
        super(title, message, cancelText, confirmText, skin, action);
    }
    
    public LargeTextInputDialog(String title, String message, String cancelText, String confirmText, Skin skin, Consumer<String> action, BooleanFunction<String> validator) {
        super(title, message, cancelText, confirmText, skin, action, validator);
    }

    @Override
    protected TextField createTextField(String messageText) {
        TextArea textArea = new TextArea("", getSkin());
        textArea.setMessageText(messageText);
        getContentTable().add(textArea).fillX();
        return textArea;
    }
    
}
