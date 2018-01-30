package com.upseil.gdx.util;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.CharArray;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntMap.Entry;
import com.badlogic.gdx.utils.IntMap.Keys;
import com.upseil.gdx.util.function.CharConsumer;
import com.upseil.gdx.util.function.FloatConsumer;
import com.upseil.gdx.util.function.IntObjectConsumer;

public class GDXCollections {
    
    private GDXCollections() { }
    
    public static void forEach(IntArray array, IntConsumer consumer) {
        int[] data = array.items;
        int size = array.size;
        for (int i = 0; i < size; i++) {
            consumer.accept(data[i]);
        }
    }
    
    public static void forEach(FloatArray array, FloatConsumer consumer) {
        float[] data = array.items;
        int size = array.size;
        for (int i = 0; i < size; i++) {
            consumer.accept(data[i]);
        }
    }
    
    public static void revertedForEach(FloatArray array, FloatConsumer consumer) {
        float[] data = array.items;
        for (int i = array.size - 1; i >= 0; i--) {
            consumer.accept(data[i]);
        }
    }

    public static void forEach(CharArray array, CharConsumer consumer) {
        char[] data = array.items;
        int size = array.size;
        for (int i = 0; i < size; i++) {
            consumer.accept(data[i]);
        }
    }
    
    public static <T> void forEach(Array<T> array, Consumer<T> consumer) {
        T[] data = array.items;
        int size = array.size;
        for (int i = 0; i < size; i++) {
            consumer.accept(data[i]);
        }
    }
    
    public static <T> void forEach(Array<T> array, IntObjectConsumer<T> consumer) {
        T[] data = array.items;
        int size = array.size;
        for (int i = 0; i < size; i++) {
            consumer.accept(i, data[i]);
        }
    }

    public static <T> void forEach(IntMap<T> intMap, IntObjectConsumer<T> consumer) {
        for (Entry<T> entry : intMap.entries()) {
            consumer.accept(entry.key, entry.value);
        }
    }

    public static <T> void forEachValue(IntMap<T> intMap, Consumer<T> consumer) {
        for (T value : intMap.values()) {
            consumer.accept(value);
        }
    }

    public static <T> void forEachKey(IntMap<T> intMap, IntConsumer consumer) {
        Keys keys = intMap.keys();
        while (keys.hasNext) {
            consumer.accept(keys.next());
        }
    }
    
    public static boolean isEmpty(Iterable<?> iterable) {
        if (iterable instanceof Collection) {
            return ((Collection<?>) iterable).isEmpty();
        }
        return !iterable.iterator().hasNext();
    }
    
}
