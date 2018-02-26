package com.upseil.gdx.scene2d.util;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.upseil.gdx.util.function.FloatSupplier;

public class SimpleGenericValue extends Value {
    
    private final FloatSupplier value;
    
    public SimpleGenericValue(FloatSupplier value) {
        this.value = value;
    }

    @Override
    public float get(Actor context) {
        return value.get();
    }
    
}
