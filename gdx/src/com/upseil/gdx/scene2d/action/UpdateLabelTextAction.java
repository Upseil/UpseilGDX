package com.upseil.gdx.scene2d.action;

import java.util.function.Supplier;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.upseil.gdx.util.function.BooleanSupplier;

public class UpdateLabelTextAction extends Action {
    
    private Label label;
    private BooleanSupplier condition;
    private Supplier<String> textSupplier;
    
    private float updateInterval;
    private float accumulator;
    
    public UpdateLabelTextAction set(float updateInterval, BooleanSupplier condition, Supplier<String> textSupplier) {
        setCondition(condition);
        setTextSupplier(textSupplier);
        setUpdateInterval(updateInterval);
        return this;
    }

    private UpdateLabelTextAction setCondition(BooleanSupplier condition) {
        this.condition = condition;
        return this;
    }

    private UpdateLabelTextAction setTextSupplier(Supplier<String> textSupplier) {
        this.textSupplier = textSupplier;
        return this;
    }

    private UpdateLabelTextAction setUpdateInterval(float updateInterval) {
        this.updateInterval = updateInterval;
        accumulator = updateInterval;
        return this;
    }
    
    @Override
    public void setActor(Actor actor) {
        if (actor != null && !(actor instanceof Label)) {
            throw new IllegalArgumentException("The actor has to be a " + Label.class.getSimpleName());
        }
        
        label = (Label) actor;
        super.setActor(actor);
    }
    
    @Override
    public boolean act(float delta) {
        accumulator += delta;
        if (accumulator >= updateInterval) {
            if (condition == null || condition.get()) {
                label.setText(textSupplier.get());
            }
            accumulator = 0;
        }
        return false;
    }
    
    @Override
    public void restart() {
        accumulator = updateInterval;
    }
    
    @Override
    public void reset() {
        super.reset();
        label = null;
        condition = null;
        textSupplier = null;
        updateInterval = 0;
        accumulator = 0;
    }
    
}
