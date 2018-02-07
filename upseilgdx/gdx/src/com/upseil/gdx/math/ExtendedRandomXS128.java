package com.upseil.gdx.math;

import com.badlogic.gdx.math.RandomXS128;

public class ExtendedRandomXS128 implements ExtendedRandom {
    
    private final RandomXS128 random;
    
    public ExtendedRandomXS128() {
        this(new RandomXS128());
    }
    
    public ExtendedRandomXS128(long seed) {
        this(new RandomXS128(seed));
    }
    
    public ExtendedRandomXS128(long seed0, long seed1) {
        this(new RandomXS128(seed0, seed1));
    }
    
    public ExtendedRandomXS128(RandomXS128 random) {
        this.random = random;
    }

    @Override
    public long randomLong() {
        return random.nextLong();
    }
    
    @Override
    public long randomLong(long exclusiveBound) {
        return random.nextLong(exclusiveBound);
    }
    
    @Override
    public int randomInt() {
        return random.nextInt();
    }
    
    @Override
    public int randomInt(int exclusiveBound) {
        return random.nextInt(exclusiveBound);
    }
    
    @Override
    public double randomDouble() {
        return random.nextDouble();
    }
    
    @Override
    public float randomFloat() {
        return random.nextFloat();
    }
    
    @Override
    public boolean randomBoolean() {
        return random.nextBoolean();
    }
    
    @Override
    public void randomBytes(byte[] bytes) {
        random.nextBytes(bytes);
    }
    
    @Override
    public void setSeed(long seed) {
        random.setSeed(seed);
    }
    
    @Override
    public void setState(long seed0, long seed1) {
        random.setState(seed0, seed1);
    }
    
    @Override
    public long getState(int seedNumber) {
        return random.getState(seedNumber);
    }
    
}
