package com.upseil.gdx.util.format;

import com.upseil.gdx.util.function.LongObjectBiFunction;

public interface TimestampFormatter extends LongFormatter, LongObjectBiFunction<String, String> {

    public static final int SecondsPerMinute = 60;
    public static final int MinutesPerHour = 60;
    public static final int HoursPerDay = 24;
    
    public static final int SecondsPerHour = SecondsPerMinute * MinutesPerHour;
    public static final int SecondsPerDay = SecondsPerHour * HoursPerDay;

    public static final TimeComponent DayComponent = new TimeComponent(SecondsPerDay * 1000);
    public static final TimeComponent HourComponent = new TimeComponent(SecondsPerHour * 1000);
    public static final TimeComponent MinuteComponent = new TimeComponent(SecondsPerMinute * 1000);
    public static final TimeComponent SecondComponent = new TimeComponent(1000);
    public static final TimeComponent MillisecondComponent = new TimeComponent(1);

    public static final String DefaultComponentSeparator = ":";
    public static final String MillisecondsSeparator = ".";

    public static final TimestampFormatter ddHHMMSS = new TimeComponentsFormatter(DayComponent, HourComponent, MillisecondComponent, SecondComponent);
    public static final TimestampFormatter HHMMSS = new TimeComponentsFormatter(HourComponent, MinuteComponent, SecondComponent);
    public static final TimestampFormatter MMSS = new TimeComponentsFormatter(MinuteComponent, SecondComponent);
    public static final TimestampFormatter SSLL = new TimeComponentsFormatter(SecondComponent, MillisecondComponent);
    
    @Override
    public default String apply(long milliseconds) {
        return apply(milliseconds, DefaultComponentSeparator);
    }
    
    @Override
    public String apply(long milliseconds, String separator);
    
    public default String format(short[] components) {
        return format(components, DefaultComponentSeparator);
    }
    
    public default String format(short[] components, String separator) {
        StringBuilder builder = new StringBuilder(components.length * 2 + separator.length() * (components.length - 1));
        short component = components[0];
        builder.append(component < 10 ? "0" : "").append(component);
        for (int index = 1; index < components.length; index++) {
            component = components[index];
            builder.append(separator).append(component < 10 ? "0" : "").append(component);
        }
        return builder.toString();
    }
    
    public static class TimeComponent {
        
        private final int millisecondsPerComponent;

        public TimeComponent(int millisecondsPerComponent) {
            this.millisecondsPerComponent = millisecondsPerComponent;
        }

        public int getMilliseconds() {
            return millisecondsPerComponent;
        }
        
        public long getMilliseconds(long value) {
            return value * millisecondsPerComponent;
        }
        
        public long getValue(long milliseconds) {
            return milliseconds / millisecondsPerComponent;
        }
        
        public long getRemainder(long milliseconds) {
            return milliseconds % millisecondsPerComponent;
        }

        public long getRemainder(long milliseconds, long componentValue) {
            return milliseconds - componentValue * millisecondsPerComponent;
        }
        
    }
    
}
