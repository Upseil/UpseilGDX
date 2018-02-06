package com.upseil.gdx.event;

import java.util.function.Consumer;

public interface EventHandler<T extends Event<T>> extends Consumer<T> {

}
