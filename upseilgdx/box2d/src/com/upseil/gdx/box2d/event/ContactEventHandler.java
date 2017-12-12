package com.upseil.gdx.box2d.event;

import java.util.function.Consumer;

import com.upseil.gdx.util.function.BooleanFunction;

@FunctionalInterface
public interface ContactEventHandler<C extends ContactEvent<C>> extends Consumer<C> {
    
    default ContactEventHandler<C> withCondition(BooleanFunction<C> condition) {
        return context -> { if (condition.apply(context)) this.accept(context); };
    }
    
}
