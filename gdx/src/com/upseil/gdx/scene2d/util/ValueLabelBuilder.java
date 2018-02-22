package com.upseil.gdx.scene2d.util;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.action;

import java.util.function.Supplier;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.upseil.gdx.scene2d.action.UpdateLabelTextAction;
import com.upseil.gdx.util.builder.ReusableBuilder;
import com.upseil.gdx.util.format.DoubleFormatter;
import com.upseil.gdx.util.format.LongFormatter;
import com.upseil.gdx.util.function.BooleanSupplier;
import com.upseil.gdx.util.function.DoubleSupplier;
import com.upseil.gdx.util.function.FloatSupplier;
import com.upseil.gdx.util.function.IntSupplier;
import com.upseil.gdx.util.function.LongSupplier;

public class ValueLabelBuilder implements ReusableBuilder<Label> {
    
    private static final String DefaultStyle = "default";
    
    private static ValueLabelBuilder instance;

    public static ValueLabelBuilder get() {
        return get(null);
    }

    public static ValueLabelBuilder get(Skin skin) {
        if (instance == null) {
            instance = new ValueLabelBuilder();
        }
        instance.reset();
        instance.setSkin(skin);
        return instance;
    }
    
    public static Label withValue(Skin skin, Supplier<String> value) {
        return get(skin).withValue(value).build();
    }
    
    private Skin skin;
    
    private Label labelToDecorate;
    private String styleName;
    
    private float updateInterval;
    private BooleanSupplier updateCondition;
    private Supplier<String> valueSupplier;

    private ValueLabelBuilder() {
        this(null);
    }
    
    public ValueLabelBuilder(Skin skin) {
        this.skin = skin;
        this.styleName = DefaultStyle;
    }
    
    // TODO Exceptions instead of overwriting fields?
    public ValueLabelBuilder decorate(Label label) {
        this.labelToDecorate = label;
        this.styleName = null;
        return this;
    }
    
    public ValueLabelBuilder withStyle(String styleName) {
        this.labelToDecorate = null;
        this.styleName = styleName;
        return this;
    }
    
    public ValueLabelBuilder withInterval(float updateInterval) {
        this.updateInterval = updateInterval;
        return this;
    }
    
    public ValueLabelBuilder updateIf(BooleanSupplier condition) {
        this.updateCondition = condition;
        return this;
    }
    
    public ValueLabelBuilder withValue(Supplier<String> value) {
        this.valueSupplier = value;
        return this;
    }
    
    public ValueLabelBuilder withValue(DoubleFormatter format, FloatSupplier value) {
        this.valueSupplier = () -> format.apply(value.get());
        return this;
    }
    
    public ValueLabelBuilder withValue(DoubleFormatter format, DoubleSupplier value) {
        this.valueSupplier = () -> format.apply(value.get());
        return this;
    }
    
    public ValueLabelBuilder withValue(LongFormatter format, IntSupplier value) {
        this.valueSupplier = () -> format.apply(value.get());
        return this;
    }
    
    public ValueLabelBuilder withValue(LongFormatter format, LongSupplier value) {
        this.valueSupplier = () -> format.apply(value.get());
        return this;
    }

    @Override
    public Label build() {
        Label label = labelToDecorate != null ? labelToDecorate : new Label("", skin, styleName);
        Action updateAction = action(UpdateLabelTextAction.class).set(updateInterval, updateCondition, valueSupplier);
        label.addAction(updateAction);
        return label;
    }
    
    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    @Override
    public ValueLabelBuilder reset() {
        skin = null;
        labelToDecorate = null;
        styleName = DefaultStyle;
        updateInterval = 0;
        updateCondition = null;
        valueSupplier = null;
        return this;
    }
    
}
