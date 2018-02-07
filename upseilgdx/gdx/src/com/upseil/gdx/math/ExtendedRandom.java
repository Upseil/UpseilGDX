package com.upseil.gdx.math;

public interface ExtendedRandom {
    
    // Standard Random API ------------------------------------------------------------------------
    
    /**
     * Returns the next pseudo-random, uniformly distributed {@code long} value
     * from this random number generator's sequence.
     */
    public long randomLong();
    
    /**
     * Returns a pseudo-random, uniformly distributed {@code long} value between
     * 0 (inclusive) and the specified value (exclusive), drawn from this random
     * number generator's sequence. The algorithm used to generate the value
     * guarantees that the result is uniform, provided that the sequence of
     * 64-bit values produced by this generator is.
     * 
     * @param n
     *            the positive bound on the random number to be returned
     */
    public long randomLong(long exclusiveBound);
    
    /**
     * Returns the next pseudo-random, uniformly distributed {@code int} value
     * from this random number generator's sequence.
     */
    public int randomInt();
    
    /**
     * Returns a pseudo-random, uniformly distributed {@code int} value between
     * 0 (inclusive) and the specified bound (exclusive), drawn from this random
     * number generator's sequence.
     * 
     * @param n
     *            the positive bound on the random number to be returned
     */
    public int randomInt(int exclusiveBound);
    
    /**
     * Returns a pseudo-random, uniformly distributed {@code double} value
     * between 0.0 and 1.0 from this random number generator's sequence.
     */
    public double randomDouble();
    
    /**
     * Returns a pseudo-random, uniformly distributed {@code float} value
     * between 0.0 and 1.0 from this random number generator's sequence.
     */
    public float randomFloat();
    
    /**
     * Returns a pseudo-random, uniformly distributed {@code boolean } value
     * from this random number generator's sequence.
     */
    public boolean randomBoolean();
    
    /**
     * Generates random bytes and places them into a user-supplied byte array.
     * The number of random bytes produced is equal to the length of the byte
     * array.
     */
    public void randomBytes(byte[] bytes);
    
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
    
    // Utility Methods ----------------------------------------------------------------------------
    
    /**
     * Returns a random number between 0 (inclusive) and the specified value
     * (inclusive).
     */
    public default int random(int bound) {
        return randomInt(bound + 1);
    }
    
    /**
     * Returns a random number between start (inclusive) and end (inclusive).
     */
    public default int random(int start, int end) {
        return start + randomInt(end - start + 1);
    }
    
    /**
     * Returns a random number between 0 (inclusive) and the specified value
     * (inclusive).
     */
    public default long random(long bound) {
        return randomLong(bound + 1);
    }
    
    /**
     * Returns a random number between start (inclusive) and end (inclusive).
     */
    public default long random(long start, long end) {
        return start + randomLong(end - start + 1);
    }
    
    /**
     * Returns true if a random value between 0 and 1 is less than the specified
     * value.
     */
    public default boolean randomBoolean(float chance) {
        return random() < chance;
    }
    
    /** Returns random number between 0.0 (inclusive) and 1.0 (exclusive). */
    public default float random() {
        return randomFloat();
    }
    
    /**
     * Returns a random number between 0 (inclusive) and the specified value
     * (exclusive).
     */
    public default float random(float bound) {
        return randomFloat() * bound;
    }
    
    /**
     * Returns a random number between start (inclusive) and end (exclusive).
     */
    public default float random(float start, float end) {
        return start + randomFloat() * (end - start);
    }
    
    /** Returns -1 or 1, randomly. */
    public default int randomSign() {
        return 1 | (randomInt() >> 31);
    }
    
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
    
}
