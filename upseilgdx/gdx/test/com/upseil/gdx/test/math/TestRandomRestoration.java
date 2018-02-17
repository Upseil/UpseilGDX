package com.upseil.gdx.test.math;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.upseil.gdx.math.ExtendedRandom;
import com.upseil.gdx.math.ExtendedRandomXS128;

public class TestRandomRestoration {

    private static final long seed = 123456789;

    private static final long seed0 = 123412340;
    private static final long seed1 = 567896789;
    
    private static final int length = 100;
    
    @Test
    public void testSetSeeds() {
        ExtendedRandom random = new ExtendedRandomXS128();
        random.setState(seed0, seed1);
        
        long[] values = new long[length];
        for (int i = 0; i < length; i++) {
            values[i] = random.randomLong();
        }

        random.setState(seed0, seed1);
        for (int i = 0; i < length; i++) {
            assertThat("Unexpected random number for index " + i, random.randomLong(), is(values[i]));
        }
    }
    
    @Test
    public void testSetSeed() {
        ExtendedRandom random = new ExtendedRandomXS128();
        random.setSeed(seed);
        
        long[] values = new long[length];
        for (int i = 0; i < length; i++) {
            values[i] = random.randomLong();
        }
        
        random.setSeed(seed);
        for (int i = 0; i < length; i++) {
            assertThat("Unexpected random number for index " + i, random.randomLong(), is(values[i]));
        }
    }
    
}
