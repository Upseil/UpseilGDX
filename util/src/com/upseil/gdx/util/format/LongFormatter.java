package com.upseil.gdx.util.format;

import java.util.function.LongFunction;

public interface LongFormatter extends LongFunction<String> {
    
    public static final LongFormatter NoFormat = value -> Long.toString(value);
    
}
