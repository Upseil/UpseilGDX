package com.upseil.gdx.util.format;

import java.util.ArrayList;

public class PercentFormat extends SimpleNumberFormat {

    private static final StringBuilder textBuilder = new StringBuilder(8);
    private static final ArrayList<PercentFormat> cache = new ArrayList<>(4);
    
    public static DoubleFormatter get(int decimals) {
        int size = decimals + 1;
        if (cache.size() < size) {
            cache.ensureCapacity(size);
            while (cache.size() < size) {
                cache.add(null);
            }
        }
        
        PercentFormat format = cache.get(decimals);
        if (format == null) {
            format = new PercentFormat(decimals);
            cache.set(decimals, format);
        }
        return format;
    }
    
    public PercentFormat(int decimals) {
        super(decimals);
    }

    @Override
    public String apply(double value) {
        textBuilder.setLength(0);
        textBuilder.append(super.apply(value * 100)).append("%");
        return textBuilder.toString();
    }
    
}
