package com.upseil.gdx.util.format;

public class RomanFormat implements DoubleFormatter {
    
    private static final int[] numbers =     {1000, 900,  500, 400,  100,  90,  50,   40,  10,   9,    5,   4,    1,   0};
    private static final String[] literals = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I", "N"};
    
    @Override
    public String apply(double value) {
        StringBuilder builder = new StringBuilder();
        if (value < 0) {
            builder.append("-");
            value = -1 * value;
        }
        return apply((int) value, builder).toString();
    }
    
    private StringBuilder apply(int value, StringBuilder builder) {
        int index = floorToIndex(value);
        int number = numbers[index];
        
        builder.append(literals[index]);
        if (value == number) {
            return builder;
        }
        
        return builder.append(apply(value - number));
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
