package com.upseil.gdx.desktop;

import java.text.DateFormat;
import java.util.Date;

import com.upseil.gdx.util.format.DateFormatter;

public class DesktopDataFormatter implements DateFormatter {
    
    private final DateFormat format;
    
    public DesktopDataFormatter(DateFormat format) {
        this.format = format;
    }

    @Override
    public String apply(Date date) {
        return format.format(date);
    }
    
}
