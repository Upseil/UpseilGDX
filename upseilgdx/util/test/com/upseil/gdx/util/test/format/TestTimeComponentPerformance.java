package com.upseil.gdx.util.test.format;

import static com.upseil.gdx.util.format.TimestampFormatter.DayComponent;

import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;

import com.upseil.gdx.util.format.TimestampFormatter.TimeComponent;

@Ignore("Benchmark")
public class TestTimeComponentPerformance {

    private static final Random Random = new Random();
    private static final int Runs = 100;
    private static final int SampleSize = 10000000;
    
    @Test
    public void testRemainderPerformance() {
        TimeComponent day = DayComponent;

        long averageRemainderDuration = 0;
        long averageRemainderWithValueDuration = 0;
        long averageRemainderWithKnownValueDuration = 0;
        
        System.out.println(String.format("Running %d Runs with sample size %d", Runs, SampleSize));
        for (int run = 0; run < Runs; run++) {
            System.out.print(".");
            long[] samples = createSampleSet();
            
            long duration = benchmarkRemainder(day, samples);
            averageRemainderDuration += duration / Runs;
            
            duration = benchmarkRemainderWithValue(day, samples);
            averageRemainderWithValueDuration += duration / Runs;
            
            duration = benchmarkRemainderWithKnownValue(day, samples);
            averageRemainderWithKnownValueDuration += duration / Runs;
        }

        System.out.println();
        System.out.println("Remainder: " + averageRemainderDuration + " ns");
        System.out.println("Remainder with value: " + averageRemainderWithValueDuration + " ns");
        System.out.println("Remainder with known value: " + averageRemainderWithKnownValueDuration + " ns");
    }

    private long[] createSampleSet() {
        long[] samples = new long[SampleSize];
        for (int index = 0; index < samples.length; index++) {
            samples[index] = Math.abs(Random.nextLong());
        }
        return samples;
    }

    private long benchmarkRemainder(TimeComponent component, long[] samples) {
        long start = System.nanoTime();
        for (long sample : samples) {
            component.getRemainder(sample);
        }
        long end = System.nanoTime();
        return end - start;
    }

    private long benchmarkRemainderWithValue(TimeComponent component, long[] samples) {
        long start = System.nanoTime();
        for (long sample : samples) {
            component.getRemainder(sample, component.getValue(sample));
        }
        long end = System.nanoTime();
        return end - start;
    }

    private long benchmarkRemainderWithKnownValue(TimeComponent component, long[] samples) {
        long[] values = new long[samples.length];
        for (int index = 0; index < samples.length; index++) {
            values[index] = component.getValue(samples[index]);
        }
        
        long start = System.nanoTime();
        for (int index = 0; index < samples.length; index++) {
            component.getRemainder(samples[index], values[index]);
        }
        long end = System.nanoTime();
        return end - start;
    }
    
}
