package com.upseil.gdx.util.format;

import java.util.function.DoubleFunction;

public interface DoubleFormatter extends DoubleFunction<String> {
    
    public enum Format { None, Abbreviation, Engineering, Percent, Roman }
    
    static final DoubleFormatter RomanFormat = new RomanFormat();
    static final DoubleFormatter NoFormat = value -> value + "";
    
    static DoubleFormatter get(Format format) {
        switch (format) {
        case Abbreviation:
            return BigNumberFormat.Abbreviation;
        case Engineering:
            return BigNumberFormat.Engineering;
        case Percent:
            return getPercentFormat(0);
        case Roman:
            return RomanFormat;
        case None:
            return NoFormat;
        }
        throw new IllegalArgumentException("No formatter for " + format + " is implemented.");
    }

    static DoubleFormatter getPercentFormat(int decimals) {
        return PercentFormat.get(decimals);
    }
    
}
