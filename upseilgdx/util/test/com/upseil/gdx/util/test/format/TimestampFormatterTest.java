package com.upseil.gdx.util.test.format;

import static com.upseil.gdx.util.format.TimestampFormatter.DayComponent;
import static com.upseil.gdx.util.format.TimestampFormatter.HourComponent;
import static com.upseil.gdx.util.format.TimestampFormatter.MinuteComponent;
import static com.upseil.gdx.util.format.TimestampFormatter.SecondComponent;
import static com.upseil.gdx.util.format.TimestampFormatter.SecondsPerHour;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.upseil.gdx.util.format.TimeComponentsFormatter;
import com.upseil.gdx.util.format.TimestampFormatter;
import com.upseil.gdx.util.format.TimestampFormatter.TimeComponent;

public class TimestampFormatterTest {
    
    @Test
    public void testTimeComponentsFormatter() {
        TimestampFormatter formatter = new TimeComponentsFormatter(HourComponent, MinuteComponent, SecondComponent);
        
        long hours = 10;
        long minutes = 24;
        long seconds = 32;
        long milliseconds = 481;
        long timestamp = createTimestamp(0, hours, minutes, seconds, milliseconds);
        
        String expectedString = "10:24:32";
        assertThat(formatter.apply(timestamp), is(expectedString));
        
        expectedString = "10-24-32";
        assertThat(formatter.apply(timestamp, "-"), is(expectedString));
        
        formatter = new TimeComponentsFormatter(MinuteComponent, SecondComponent, HourComponent);
        expectedString = "10:24:32";
        assertThat(formatter.apply(timestamp), is(expectedString));
    }
    
    @Test
    public void testTimeComponent() {
        TimeComponent component = new TimeComponent(SecondsPerHour * 1000);
        
        long expectedValue = 5;
        long expectedRemainder = 12345;
        long milliseconds = createTimestamp(0, expectedValue, 0, 0, expectedRemainder);
        
        assertThat(component.getValue(milliseconds), is(expectedValue));
        assertThat(component.getRemainder(milliseconds), is(expectedRemainder));
        assertThat(component.getRemainder(milliseconds, expectedValue), is(expectedRemainder));
    }
    
    private long createTimestamp(long days, long hours, long minutes, long seconds, long milliseconds) {
        long timestamp = 0;
        timestamp += DayComponent.getMilliseconds(days);
        timestamp += HourComponent.getMilliseconds(hours);
        timestamp += MinuteComponent.getMilliseconds(minutes);
        timestamp += SecondComponent.getMilliseconds(seconds);
        timestamp += milliseconds;
        return timestamp;
    }
    
}
