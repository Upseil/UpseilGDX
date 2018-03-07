package com.upseil.gdx.util.format;

import java.util.function.DoubleFunction;

public interface DoubleFormatter extends DoubleFunction<String> {
    
    public enum Format { None, Rounded, Simple, Abbreviation, Engineering, Percent, Roman }
    
    public static final DoubleFormatter RomanFormat = new RomanFormat();
    public static final DoubleFormatter NoFormat = value -> Double.toString(value);
    public static final DoubleFormatter RoundedFormat = value -> Integer.toString((int) Math.round(value));
    
    public static DoubleFormatter get(Format format) {
        switch (format) {
        case Abbreviation:
            return BigNumberFormat.Abbreviation;
        case Engineering:
            return BigNumberFormat.Engineering;
        case Percent:
            return getPercentFormat(0);
        case Roman:
            return RomanFormat;
        case Simple:
            return getSimpleFormat(2);
        case Rounded:
            return RoundedFormat;
        case None:
            return NoFormat;
        }
        throw new IllegalArgumentException("No formatter for " + format + " is implemented.");
    }

    public static DoubleFormatter getSimpleFormat(int decimals) {
        return SimpleNumberFormat.get(decimals);
    }

    public static DoubleFormatter getPercentFormat(int decimals) {
        return PercentFormat.get(decimals);
    }
    
}
