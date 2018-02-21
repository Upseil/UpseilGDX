package com.upseil.gdx.util.format;

import java.util.Date;

public interface DateFormatter extends Formatter<Date>, TimestampFormatter {
    
    public static final Date tempDate = new Date();

    @Override
    default String apply(long timestamp) {
        tempDate.setTime(timestamp);
        return apply(tempDate);
    }

    @Override
    default String apply(long timestamp, String separator) {
        return apply(timestamp);
    }
    
    
    
}
