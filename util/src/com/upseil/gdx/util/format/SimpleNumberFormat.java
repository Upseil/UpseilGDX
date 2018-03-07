package com.upseil.gdx.util.format;

import java.util.ArrayList;

public class SimpleNumberFormat implements DoubleFormatter {
    
    private static final ArrayList<SimpleNumberFormat> cache = new ArrayList<>(4);
    
    public static DoubleFormatter get(int decimals) {
        int size = decimals + 1;
        if (cache.size() < size) {
            cache.ensureCapacity(size);
            while (cache.size() < size) {
                cache.add(null);
            }
        }
        
        SimpleNumberFormat format = cache.get(decimals);
        if (format == null) {
            format = new SimpleNumberFormat(decimals);
            cache.set(decimals, format);
        }
        return format;
    }
    
    private final double precisionFactor;
    
    public SimpleNumberFormat(int decimals) {
        double precisionFactorAccumulator = 1;
        for (int i = 0; i < decimals; i++) {
            precisionFactorAccumulator *= 10;
        }
        this.precisionFactor = precisionFactorAccumulator;
    }

    @Override
    public String apply(double value) {
        double precisionValue = Math.round(value * precisionFactor) / precisionFactor;
        if (precisionValue == Math.floor(precisionValue)) {
            return Integer.toString((int) precisionValue);
        } else {
            return Double.toString(precisionValue);
        }
    }
    
}
