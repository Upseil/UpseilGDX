package com.upseil.gdx.scene2d.action;

import java.util.function.Supplier;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class UpdateLabelTextAction extends Action {
    
    // TODO [Performance] Add Conditional Updates
    
    private Label label;
    private Supplier<String> textSupplier;
    
    private float updateInterval;
    private float accumulator;
    
    public UpdateLabelTextAction set(Supplier<String> textSupplier) {
        return set(0, textSupplier);
    }
    
    public UpdateLabelTextAction set(float updateInterval, Supplier<String> textSupplier) {
        this.textSupplier = textSupplier;
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
            label.setText(textSupplier.get());
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
        textSupplier = null;
        updateInterval = 0;
        accumulator = 0;
    }
    
}
