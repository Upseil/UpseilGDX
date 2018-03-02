package com.upseil.gdx.math;

import java.util.Random;

public interface ExtendedRandom {
    
    public Random asRandom();
    
    // long ---------------------------------------------------------------------------------------
    
    /**
     * Returns a uniformly distributed random number
     */
    public long randomLong();

    /**
     * Returns a random number between 0 (inclusive) and the specified value (exclusive).
     */
    public long randomLongExclusive(long exclusiveBound);
    
    /**
     * Returns a random number between 0 (inclusive) and the specified value (inclusive).
     */
    public default long randomLongInclusive(long bound) {
        return randomLongExclusive(bound + 1);
    }
    
    /**
     * Returns a random number between start (inclusive) and end (inclusive).
     */
    public default long randomLong(long start, long end) {
        return start + randomLongExclusive(end - start + 1);
    }

    // int ----------------------------------------------------------------------------------------

    /**
     * Returns a uniformly distributed random number
     */
    public int randomInt();

    /**
     * Returns a random number between 0 (inclusive) and the specified value (exclusive).
     */
    public int randomIntExclusive(int exclusiveBound);
    
    /**
     * Returns a random number between 0 (inclusive) and the specified value (inclusive).
     */
    public default int randomIntInclusive(int bound) {
        return randomIntExclusive(bound + 1);
    }
    
    /**
     * Returns a random number between start (inclusive) and end (inclusive).
     */
    public default int randomInt(int start, int end) {
        return start + randomIntExclusive(end - start + 1);
    }
    
    /** Returns -1 or 1, randomly. */
    public default int randomSign() {
        return 1 | (randomInt() >> 31);
    }

    // double -------------------------------------------------------------------------------------

    /** Returns random number between 0 (inclusive) and 1 (exclusive). */
    public double randomDouble();
    
    /**
     * Returns a random number between 0 (inclusive) and the specified value (exclusive).
     */
    public default double randomDoubleExclusive(double bound) {
        return randomDouble() * bound;
    }
    
    /**
     * Returns a random number between start (inclusive) and end (exclusive).
     */
    public default double randomDouble(double start, double end) {
        return start + randomDouble() * (end - start);
    }

    // float --------------------------------------------------------------------------------------

    /** Returns random number between 0 (inclusive) and 1 (exclusive). */
    public float randomFloat();
    
    /**
     * Returns a random number between 0 (inclusive) and the specified value (exclusive).
     */
    public default float randomFloatExclusive(float bound) {
        return randomFloat() * bound;
    }
    
    /**
     * Returns a random number between start (inclusive) and end (exclusive).
     */
    public default float randomFloat(float start, float end) {
        return start + randomFloat() * (end - start);
    }

    // boolean ------------------------------------------------------------------------------------
    
    /**
     * Returns a pseudo-random, uniformly distributed {@code boolean } value
     * from this random number generator's sequence.
     */
    public boolean randomBoolean();
    
    /**
     * Returns true if a random value between 0 and 1 is less than the specified
     * value.
     */
    public default boolean randomBoolean(float chance) {
        return randomFloat() < chance;
    }

    // bytes --------------------------------------------------------------------------------------
    
    /**
     * Generates random bytes and places them into a user-supplied byte array.
     * The number of random bytes produced is equal to the length of the byte
     * array.
     */
    public void randomBytes(byte[] bytes);

    // Triangular Distribution --------------------------------------------------------------------
    
    /**
     * Returns a triangularly distributed random number between -1.0 (exclusive)
     * and 1.0 (exclusive), where values around zero are more likely.
     * <p>
     * This is an optimized version of
     * {@link #randomTriangular(float, float, float) randomTriangular(-1, 1, 0)}
     */
    public default float randomTriangular() {
        return randomFloat() - randomFloat();
    }
    
    /**
     * Returns a triangularly distributed random number between {@code -max}
     * (exclusive) and {@code max} (exclusive), where values around zero are
     * more likely.
     * <p>
     * This is an optimized version of
     * {@link #randomTriangular(float, float, float) randomTriangular(-max, max,
     * 0)}
     * 
     * @param max
     *            the upper limit
     */
    public default float randomTriangular(float max) {
        return (randomFloat() - randomFloat()) * max;
    }
    
    /**
     * Returns a triangularly distributed random number between {@code min}
     * (inclusive) and {@code max} (exclusive), where the {@code mode} argument
     * defaults to the midpoint between the bounds, giving a symmetric
     * distribution.
     * <p>
     * This method is equivalent of
     * {@link #randomTriangular(float, float, float) randomTriangular(min, max,
     * (min + max) * .5f)}
     * 
     * @param min
     *            the lower limit
     * @param max
     *            the upper limit
     */
    public default float randomTriangular(float min, float max) {
        return randomTriangular(min, max, (min + max) * 0.5f);
    }
    
    /**
     * Returns a triangularly distributed random number between {@code min}
     * (inclusive) and {@code max} (exclusive), where values around {@code mode}
     * are more likely.
     * 
     * @param min
     *            the lower limit
     * @param max
     *            the upper limit
     * @param mode
     *            the point around which the values are more likely
     */
    public default float randomTriangular(float min, float max, float mode) {
        float u = randomFloat();
        float d = max - min;
        if (u <= (mode - min) / d)
            return min + (float) Math.sqrt(u * d * (mode - min));
        return max - (float) Math.sqrt((1 - u) * d * (max - mode));
    }

    // State --------------------------------------------------------------------------------------
    
    /**
     * Sets the internal seed of this generator based on the given {@code long}
     * value.
     * 
     * @param seed
     *            a nonzero seed for this generator (if zero, the generator will
     *            be seeded with {@link Long#MIN_VALUE})
     */
    public void setSeed(long seed);
    
    /**
     * Sets the internal state of this generator.
     * 
     * @param seed0 the first part of the internal state
     * @param seed1 the second part of the internal state
     */
    public void setState(long seed0, long seed1);
    
    /**
     * Returns the internal seeds to allow state saving.
     * 
     * @param seedNumber
     *            must be 0 or 1, designating which of the 2 long seeds to
     *            return
     * @return the internal seed that can be used in {@link #setState(long, long)}
     */
    public long getState(int seedNumber);
    
}
