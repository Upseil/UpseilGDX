package com.upseil.gdx.util.format;

import java.util.function.DoubleFunction;

public interface DoubleFormatter extends DoubleFunction<String> {
    
    // TODO [Performance] Reuse string builders
    
    public enum Format { None, Rounded, Simple, Abbreviation, Engineering, Percent, Roman }
    
    public static final DoubleFormatter RomanFormat = new RomanFormat();
    public static final DoubleFormatter NoFormat = value -> value + "";
    public static final DoubleFormatter RoundedFormat = value -> (int) Math.round(value) + "";
    
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
        if (decimals == 0) return RoundedFormat;
        return PercentFormat.get(decimals);
    }
    
}
