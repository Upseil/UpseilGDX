package com.upseil.gdx.math;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;

public final class Random {

    private static final RandomXS128 random = new RandomXS128();

    /** Returns a random number between 0 (inclusive) and the specified value (inclusive). */
    public static int random (int range) {
        return random.nextInt(range + 1);
    }

    /** Returns a random number between start (inclusive) and end (inclusive). */
    public static int random (int start, int end) {
        return start + random.nextInt(end - start + 1);
    }

    /** Returns a random number between 0 (inclusive) and the specified value (inclusive). */
    public static long random (long range) {
        return (long)(random.nextDouble() * range);
    }

    /** Returns a random number between start (inclusive) and end (inclusive). */
    public static long random (long start, long end) {
        return start + (long)(random.nextDouble() * (end - start));
    }

    /** Returns a random boolean value. */
    public static boolean randomBoolean () {
        return random.nextBoolean();
    }

    /** Returns true if a random value between 0 and 1 is less than the specified value. */
    public static boolean randomBoolean (float chance) {
        return MathUtils.random() < chance;
    }

    /** Returns random number between 0.0 (inclusive) and 1.0 (exclusive). */
    public static float random () {
        return random.nextFloat();
    }

    /** Returns a random number between 0 (inclusive) and the specified value (exclusive). */
    public static float random (float range) {
        return random.nextFloat() * range;
    }

    /** Returns a random number between start (inclusive) and end (exclusive). */
    public static float random (float start, float end) {
        return start + random.nextFloat() * (end - start);
    }

    /** Returns -1 or 1, randomly. */
    public static int randomSign () {
        return 1 | (random.nextInt() >> 31);
    }

    /** Returns a triangularly distributed random number between -1.0 (exclusive) and 1.0 (exclusive), where values around zero are
     * more likely.
     * <p>
     * This is an optimized version of {@link #randomTriangular(float, float, float) randomTriangular(-1, 1, 0)} */
    public static float randomTriangular () {
        return random.nextFloat() - random.nextFloat();
    }

    /** Returns a triangularly distributed random number between {@code -max} (exclusive) and {@code max} (exclusive), where values
     * around zero are more likely.
     * <p>
     * This is an optimized version of {@link #randomTriangular(float, float, float) randomTriangular(-max, max, 0)}
     * @param max the upper limit */
    public static float randomTriangular (float max) {
        return (random.nextFloat() - random.nextFloat()) * max;
    }

    /** Returns a triangularly distributed random number between {@code min} (inclusive) and {@code max} (exclusive), where the
     * {@code mode} argument defaults to the midpoint between the bounds, giving a symmetric distribution.
     * <p>
     * This method is equivalent of {@link #randomTriangular(float, float, float) randomTriangular(min, max, (min + max) * .5f)}
     * @param min the lower limit
     * @param max the upper limit */
    public static float randomTriangular (float min, float max) {
        return randomTriangular(min, max, (min + max) * 0.5f);
    }

    /** Returns a triangularly distributed random number between {@code min} (inclusive) and {@code max} (exclusive), where values
     * around {@code mode} are more likely.
     * @param min the lower limit
     * @param max the upper limit
     * @param mode the point around which the values are more likely */
    public static float randomTriangular (float min, float max, float mode) {
        float u = random.nextFloat();
        float d = max - min;
        if (u <= (mode - min) / d) return min + (float)Math.sqrt(u * d * (mode - min));
        return max - (float)Math.sqrt((1 - u) * d * (max - mode));
    }
    
    public static long getState(int seedNumber) {
        return random.getState(seedNumber);
    }
    
    public static void setState(long seed0, long seed1) {
        random.setState(seed0, seed1);
    }
    
}
