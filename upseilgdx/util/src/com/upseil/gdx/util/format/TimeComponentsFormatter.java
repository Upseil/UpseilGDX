package com.upseil.gdx.util.format;

import java.util.Arrays;

public class TimeComponentsFormatter implements TimestampFormatter {
    
    private final TimeComponent[] components;
    private final byte[] componentValues;
    
    public TimeComponentsFormatter(TimeComponent... components) {
        this.components = components;
        Arrays.sort(this.components, (c1, c2) -> Long.compare(c2.getMilliseconds(), c1.getMilliseconds()));
        componentValues = new byte[components.length];
    }

    @Override
    public String apply(long milliseconds, String componentSeparator) {
        setComponentValues(milliseconds);
        return format(componentValues, componentSeparator);
    }

    private void setComponentValues(long milliseconds) {
        for (int index = 0; index < components.length; index++) {
            TimeComponent component = components[index];
            componentValues[index] = (byte) component.getValue(milliseconds);
            milliseconds = component.getRemainder(milliseconds, componentValues[index]);
        }
    }
    
}
