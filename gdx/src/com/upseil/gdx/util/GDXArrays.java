package com.upseil.gdx.util;

import java.util.Arrays;
import java.util.Random;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;

public class GDXArrays {
    
    private GDXArrays() { }
    
    // Vector Flattening --------------------------------------------------------------------------
    
    // ---- To FloatArray -------------------------------------------------------------------------
    
    // TODO Tests
    public static FloatArray flattenToFloatArray(Vector2[] vectors) {
        return flattenToFloatArray(vectors, 0, vectors.length);
    }
    
    public static FloatArray flattenToFloatArray(Vector2[] vectors, int offset, int length) {
        FloatArray result = new FloatArray(length * 2);
        flatten(vectors, offset, length, result);
        return result;
    }
    
    public static void flatten(Vector2[] vectors, FloatArray target) {
        flatten(vectors, 0, vectors.length, target);
    }
    
    public static void flatten(Vector2[] vectors, int offset, int length, FloatArray target) {
        target.setSize(length * 2);
        flatten(vectors, offset, length, target.items);
    }
    
    // ---- To float[] ----------------------------------------------------------------------------
    
    public static float[] flattenToArray(Vector2[] vectors) {
        return flattenToArray(vectors, 0, vectors.length);
    }
    
    public static float[] flattenToArray(Vector2[] vectors, int offset, int length) {
        float[] result = new float[length * 2];
        flatten(vectors, offset, length, result);
        return result;
    }
    
    public static void flatten(Vector2[] vectors, float[] target) {
        if (vectors.length * 2 != target.length) {
            throw new IllegalArgumentException("The given target array has to be exactly twice as large as the given vector array: "
                    + "vectors.length = " + vectors.length + ", target.length = " + target.length);
        }
        flattenUnsafe(vectors, 0, vectors.length, target);
    }

    public static void flatten(Vector2[] vectors, int offset, int length, float[] target) {
        if (length * 2 != target.length) {
            throw new IllegalArgumentException("The given target array has to be exactly twice as large as the given length: "
                    + "length = " + vectors.length + ", target.length = " + target.length);
        }
        flattenUnsafe(vectors, offset, length, target);
    }
    
    private static void flattenUnsafe(Vector2[] vectors, int offset, int length, float[] target) {
        int bound = offset + length;
        for (int i = 0, j = offset; j < bound; i += 2, j++) {
            target[i] = vectors[j].x;
            target[i + 1] = vectors[j].y;
        }
    }
    
    // Shuffling ----------------------------------------------------------------------------------

    /**
     * Shuffles the given array with the Fisher-Yates algorithm, using {@link MathUtils#random}.
     * 
     * @param array The array to shuffle
     * 
     * @see #shuffle(T[], Random)
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#The_modern_algorithm">Fisher-Yates on Wikipedia</a>
     */
    public static <T> void shuffle(T[] array) {
        shuffle(array, MathUtils.random);
    }

    /**
     * Shuffles the given array with the Fisher-Yates algorithm, using the given {@link Random}.
     * 
     * @param array The array to shuffle
     * @param random The random number generator
     * 
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#The_modern_algorithm">Fisher-Yates on Wikipedia</a>
     */
    public static <T> void shuffle(T[] array, Random random) {
        for (int i = array.length - 1; i >= 0; i--) {
            int j = random.nextInt(i+1);
            swap(array, i, j);
        }
    }

    /**
     * Shuffles the given array with the Fisher-Yates algorithm, using {@link MathUtils#random}.
     * 
     * @param array The array to shuffle
     * 
     * @see #shuffle(int[], Random)
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#The_modern_algorithm">Fisher-Yates on Wikipedia</a>
     */
    public static void shuffle(int[] array) {
        shuffle(array, MathUtils.random);
    }

    /**
     * Shuffles the given array with the Fisher-Yates algorithm, using the given {@link Random}.
     * 
     * @param array The array to shuffle
     * @param random The random number generator
     * 
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#The_modern_algorithm">Fisher-Yates on Wikipedia</a>
     */
    public static void shuffle(int[] array, Random random) {
        for (int i = array.length - 1; i >= 0; i--) {
            int j = random.nextInt(i+1);
            swap(array, i, j);
        }
    }
    
    // Swapping -----------------------------------------------------------------------------------
    
    public static <T> void swap(T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
    
    public static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
    
    // Clearing -----------------------------------------------------------------------------------
    
    public static <T> void clear(T[] array) {
        fill(array, null);
    }
    
    public static void clear(boolean[] array) {
        fill(array, false);
    }
    
    public static void clear(int[] array) {
        fill(array, 0);
    }
    
    public static void clear(float[] array) {
        fill(array, 0);
    }
    
    public static void clear(double[] array) {
        fill(array, 0);
    }
    
    public static <T> void clear(T[][] table) {
        fill(table, null);
    }
    
    // Filling ------------------------------------------------------------------------------------
    
    public static <T> void fill(T[] array, T value) {
        Arrays.fill(array, value);
    }
    
    public static void fill(boolean[] array, boolean value) {
        Arrays.fill(array, value);
    }
    
    public static void fill(int[] array, int value) {
        Arrays.fill(array, value);
    }
    
    public static void fill(float[] array, float value) {
        Arrays.fill(array, value);
    }
    
    public static void fill(double[] array, double value) {
        Arrays.fill(array, value);
    }
    
    public static <T> void fill(T[][] table, T value) {
        for (T[] row : table) {
            fill(row, value);
        }
    }
    
}
