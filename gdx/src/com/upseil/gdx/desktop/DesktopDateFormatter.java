package com.upseil.gdx.desktop;

import java.text.DateFormat;
import java.util.Date;

import com.upseil.gdx.util.format.DateFormatter;

public class DesktopDateFormatter implements DateFormatter {
    
    private final DateFormat format;
    
    public DesktopDateFormatter(DateFormat format) {
        this.format = format;
    }

    @Override
    public String apply(Date date) {
        return format.format(date);
    }
    
}
