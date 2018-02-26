package com.upseil.gdx.scene2d.util;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.upseil.gdx.util.function.FloatSupplier;
import com.upseil.gdx.util.function.ObjectFloatFunction;

public class Values {
    
    public static Value floatValue(FloatSupplier value) {
        return new SimpleGenericValue(value);
    }
    
    public static Value floatValue(ObjectFloatFunction<Actor> value) {
        return new GenericValue(value);
    }
    
    private Values() { }
    
}
