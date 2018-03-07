package com.upseil.gdx.util.format;

import java.util.Arrays;

public class TimeComponentsFormatter implements TimestampFormatter {
    
    private static final StringBuilder textBuilder = new StringBuilder();
    
    private final TimeComponent[] components;
    private final short[] componentValues;
    
    private final boolean hasMillisecondComponent;
    private short millisecondsComponent;
    
    public TimeComponentsFormatter(TimeComponent... components) {
        this.components = components;
        Arrays.sort(this.components, (c1, c2) -> Long.compare(c2.getMilliseconds(), c1.getMilliseconds()));
        hasMillisecondComponent = this.components[this.components.length - 1] == MillisecondComponent;
        componentValues = new short[components.length - (hasMillisecondComponent ? 1 :0)];
    }

    @Override
    public String apply(long milliseconds, String componentSeparator) {
        setComponentValues(milliseconds);
        
        textBuilder.setLength(0);
        format(componentValues, componentSeparator, textBuilder);
        if (hasMillisecondComponent) {
            textBuilder.append(MillisecondsSeparator);
            if (millisecondsComponent < 100) textBuilder.append("0");
            if (millisecondsComponent < 10) textBuilder.append("0");
            textBuilder.append(millisecondsComponent);
        }
        return textBuilder.toString();
    }

    private void setComponentValues(long milliseconds) {
        for (int index = 0; index < componentValues.length; index++) {
            TimeComponent component = components[index];
            componentValues[index] = (short) component.getValue(milliseconds);
            milliseconds = component.getRemainder(milliseconds, componentValues[index]);
        }
        if (hasMillisecondComponent) {
            millisecondsComponent = (short) milliseconds;
        }
    }
    
}
