package com.upseil.gdx.scene2d.util;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.upseil.gdx.util.function.ObjectFloatFunction;

public class GenericValue extends Value {
    
    private final ObjectFloatFunction<Actor> value;
    
    public GenericValue(ObjectFloatFunction<Actor> value) {
        this.value = value;
    }

    @Override
    public float get(Actor context) {
        return value.apply(context);
    }
    
}
