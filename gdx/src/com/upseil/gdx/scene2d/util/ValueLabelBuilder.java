package com.upseil.gdx.scene2d.util;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.action;

import java.util.function.Supplier;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.upseil.gdx.scene2d.action.UpdateLabelTextAction;
import com.upseil.gdx.util.builder.Builder;
import com.upseil.gdx.util.format.DoubleFormatter;
import com.upseil.gdx.util.format.LongFormatter;
import com.upseil.gdx.util.function.BooleanSupplier;
import com.upseil.gdx.util.function.DoubleSupplier;
import com.upseil.gdx.util.function.FloatSupplier;
import com.upseil.gdx.util.function.IntSupplier;
import com.upseil.gdx.util.function.LongSupplier;

public class ValueLabelBuilder implements Builder<Label> {
    
    private static final String DefaultStyle = "default";
    
    private static ValueLabelBuilder instance;

    public static ValueLabelBuilder newLabel(Skin skin) {
        return newLabel(skin, DefaultStyle);
    }

    public static ValueLabelBuilder newLabel(Skin skin, String styleName) {
        return getInstance().reset(skin, styleName);
    }
    
    public static ValueLabelBuilder decorate(Label label) {
        return getInstance().reset(label);
    }

    private static ValueLabelBuilder getInstance() {
        if (instance == null) {
            instance = new ValueLabelBuilder();
        }
        return instance;
    }
    
    public static Label withValue(Skin skin, Supplier<String> value) {
        return newLabel(skin).withValue(value).build();
    }
    
    private Skin skin;
    private String styleName;
    
    private Label labelToDecorate;
    
    private float updateInterval;
    private BooleanSupplier updateCondition;
    private Supplier<String> valueSupplier;

    private ValueLabelBuilder() { }
    
    public ValueLabelBuilder(Skin skin) {
        reset(skin);
    }

    public ValueLabelBuilder(Skin skin, String styleName) {
        reset(skin, styleName);
    }
    
    public ValueLabelBuilder(Label labelToDecorate) {
        reset(labelToDecorate);
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
    
    public ValueLabelBuilder withValue(FloatSupplier value) {
        return withValue(DoubleFormatter.NoFormat, value);
    }
    
    public ValueLabelBuilder withValue(DoubleFormatter format, FloatSupplier value) {
        this.valueSupplier = () -> format.apply(value.get());
        return this;
    }
    
    public ValueLabelBuilder withValue(DoubleSupplier value) {
        return withValue(DoubleFormatter.NoFormat, value);
    }
    
    public ValueLabelBuilder withValue(DoubleFormatter format, DoubleSupplier value) {
        this.valueSupplier = () -> format.apply(value.get());
        return this;
    }
    
    public ValueLabelBuilder withValue(IntSupplier value) {
        return withValue(LongFormatter.NoFormat, value);
    }
    
    public ValueLabelBuilder withValue(LongFormatter format, IntSupplier value) {
        this.valueSupplier = () -> format.apply(value.get());
        return this;
    }
    
    public ValueLabelBuilder withValue(LongSupplier value) {
        return withValue(LongFormatter.NoFormat, value);
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
    
    public ValueLabelBuilder reset(Skin skin) {
        return reset(skin, DefaultStyle);
    }
    
    public ValueLabelBuilder reset(Skin skin, String styleName) {
        reset();
        this.skin = skin;
        this.styleName = styleName;
        return this;
    }
    
    public ValueLabelBuilder reset(Label labelToDecorate) {
        reset();
        this.labelToDecorate = labelToDecorate;
        return this;
    }

    public ValueLabelBuilder reset() {
        skin = null;
        styleName = null;
        
        labelToDecorate = null;
        
        updateInterval = 0;
        updateCondition = null;
        valueSupplier = null;
        return this;
    }
    
}
