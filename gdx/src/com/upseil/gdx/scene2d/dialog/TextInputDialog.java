package com.upseil.gdx.scene2d.dialog;

import java.util.function.Consumer;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.upseil.gdx.util.function.BooleanFunction;

public class TextInputDialog extends AbstractTextInputDialog {
    
    public TextInputDialog(String title, String message, String cancelText, String confirmText, Skin skin, Consumer<String> action) {
        super(title, message, cancelText, confirmText, skin, action);
    }

    public TextInputDialog(String title, String message, String cancelText, String confirmText, Skin skin, Consumer<String> action, BooleanFunction<String> validator) {
        super(title, message, cancelText, confirmText, skin, action, validator);
    }
    
    @Override
    protected TextField createTextField(String messageText) {
        TextField textField = new TextField("", getSkin());
        textField.setMessageText(messageText);
        getContentTable().add(textField).fillX();
        return textField;
    }
    
}
