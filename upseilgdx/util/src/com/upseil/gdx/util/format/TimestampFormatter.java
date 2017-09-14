package com.upseil.gdx.util.format;

public interface TimestampFormatter extends LongFormatter {

    public static final int secondsPerMinute = 60;
    public static final int minutesPerHour = 60;
    public static final int hoursPerDay = 24;
    
    public static final int secondsPerHour = secondsPerMinute * minutesPerHour;
    public static final int secondsPerDay = secondsPerHour * hoursPerDay;
    
    public static final TimestampFormatter ddHHmmss = milliseconds -> {
        long seconds = milliseconds / 1000;
        byte[] components = new byte[4];
        
        components[0] = (byte) (seconds / secondsPerDay);
        seconds = seconds % secondsPerDay;
        components[1] = (byte) (seconds / secondsPerHour);
        seconds = seconds % secondsPerHour;
        components[2] = (byte) (seconds / secondsPerMinute);
        seconds = seconds % secondsPerMinute;
        components[3] = (byte) seconds;
        
        return format(components);
    };
    
    static String format(byte[] components) {
        StringBuilder builder = new StringBuilder(components.length * 3 - 1); // n * 2 + n - 1
        String separator = "";
        for (byte component : components) {
            builder.append(separator).append(component < 10 ? "0" : "").append(component);
            separator = ":";
        }
        return builder.toString();
    }
    
}
