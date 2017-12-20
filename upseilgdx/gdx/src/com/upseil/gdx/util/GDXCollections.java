package com.upseil.gdx.util;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.CharArray;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntMap.Entry;
import com.upseil.gdx.util.function.CharConsumer;
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
    
    public static boolean isEmpty(Iterable<?> iterable) {
        if (iterable instanceof Collection) {
            return ((Collection<?>) iterable).isEmpty();
        }
        return !iterable.iterator().hasNext();
    }

    
}