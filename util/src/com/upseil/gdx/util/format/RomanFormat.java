package com.upseil.gdx.util.format;

public class RomanFormat implements DoubleFormatter {

    private static final StringBuilder textBuilder = new StringBuilder(8);
    private static final int[]    numbers  = {1000, 900,  500, 400,  100,  90,  50,   40,  10,   9,    5,   4,    1,   0};
    private static final String[] literals = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I", "N"};
    
    @Override
    public String apply(double doubleValue) {
        textBuilder.setLength(0);
        int value = (int) doubleValue;
        if (value < 0) {
            textBuilder.append("-");
            value = -1 * value;
        }
        
        do {
            int index = floorToIndex(value);
            textBuilder.append(literals[index]);
            value -= numbers[index];
        } while (value > 0);
        return textBuilder.toString();
    }

    private int floorToIndex(int value) {
        for (int index = 0; index < numbers.length; index++) {
            if (value >= numbers[index]) {
                return index;
            }
        }
        throw new IllegalArgumentException("Can't find index for value " + value);
    }
    
}
