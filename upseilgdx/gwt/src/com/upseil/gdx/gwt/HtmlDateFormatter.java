package com.upseil.gdx.gwt;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.upseil.gdx.util.format.DateFormatter;

public class HtmlDateFormatter implements DateFormatter {
    
    private final DateTimeFormat format;
    
    public HtmlDateFormatter(DateTimeFormat format) {
        this.format = format;
    }

    @Override
    public String apply(Date date) {
        return format.format(date);
    }
    
}
