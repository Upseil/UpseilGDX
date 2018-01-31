package com.upseil.gdx.box2d.event;

import java.util.function.Consumer;

import com.upseil.gdx.util.function.BooleanFunction;

@FunctionalInterface
public interface ContactEventHandler<E extends ContactEvent<E>> extends Consumer<E> {
    
    default ContactEventHandler<E> withCondition(BooleanFunction<E> condition) {
        return context -> { if (condition.apply(context)) this.accept(context); };
    }
    
}
