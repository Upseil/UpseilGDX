package com.upseil.gdx.artemis.util;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;

public class ArtemisCollections {
    
    private ArtemisCollections() { }
    
    public static void forEach(IntBag bag, IntConsumer consumer) {
        int[] data = bag.getData();
        int size = bag.size();
        for (int i = 0; i < size; i++) {
            consumer.accept(data[i]);
        }
    }
    
    public static <T extends Component> void forEachComponent(IntBag entities, ComponentMapper<T> mapper, Consumer<T> consumer) {
        int[] data = entities.getData();
        int size = entities.size();
        for (int i = 0; i < size; i++) {
            consumer.accept(mapper.get(data[i]));
        }
    }
    
    public static <T1 extends Component, T2 extends Component> void forEachComponent(
            IntBag entities, ComponentMapper<T1> mapper1, ComponentMapper<T2> mapper2, BiConsumer<T1, T2> consumer) {
        int[] data = entities.getData();
        int size = entities.size();
        for (int i = 0; i < size; i++) {
            consumer.accept(mapper1.get(data[i]), mapper2.get(data[i]));
        }
    }
    
}
