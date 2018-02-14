package com.upseil.gdx.scene2d.dialog;

import java.util.function.Consumer;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.upseil.gdx.util.function.BooleanFunction;

public abstract class AbstractTextInputDialog extends InputDialog<String> {
    
    private final TextField textField;
    
    public AbstractTextInputDialog(String title, String message, String cancelText, String confirmText, Skin skin, Consumer<String> action) {
        this(title, message, cancelText, confirmText, skin, action, null);
    }

    public AbstractTextInputDialog(String title, String message, String cancelText, String confirmText, Skin skin, Consumer<String> action, BooleanFunction<String> validator) {
        super(title, cancelText, confirmText, skin, action);
        
        textField = createTextField(message);
        if (validator != null) {
            textField.setTextFieldListener((textField, c) -> setValid(validator.apply(textField.getText())));
        } else {
            setValid(true);
        }
    }

    protected abstract TextField createTextField(String messageText);
    
    @Override
    public AbstractDialog show(Stage stage, Action action) {
        super.show(stage, action);
        stage.setKeyboardFocus(textField);
        return this;
    }
    
    public TextField getTextField() {
        return textField;
    }
    
    public Cell<TextField> getTextFieldCell() {
        return getContentTable().getCell(textField);
    }

    @Override
    protected String getResult() {
        return textField.getText();
    }
    
}
