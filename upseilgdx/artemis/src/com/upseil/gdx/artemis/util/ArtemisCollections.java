package com.upseil.gdx.artemis.util;

import java.util.function.IntConsumer;

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
    
}
