package com.upseil.gdx.gwt.util;

import com.badlogic.gdx.ApplicationLogger;

public class BrowserConsoleLogger implements ApplicationLogger {

    @Override
    public void log (String tag, String message, Throwable exception) {
        log(tag, message + "\n" + getMessages(exception));
    }

    @Override
    public void error (String tag, String message) {
        log(tag, message);
    }

    @Override
    public void error (String tag, String message, Throwable exception) {
        log(tag,  message + "\n" + getMessages(exception));
    }

    @Override
    public void debug (String tag, String message) {
        log(tag, message);
    }

    @Override
    public void debug (String tag, String message, Throwable exception) {
        log(tag, message + "\n" + getMessages(exception));
    }

    private String getMessages (Throwable e) {
        StringBuffer buffer = new StringBuffer();
        while (e != null) {
            buffer.append(e.getMessage() + "\n");
            e = e.getCause();
        }
        return buffer.toString();
    }

    @Override
    public void log(String tag, String text) {
        logToConsole(tag + ": " + text);
    }
    
    private native void logToConsole(String text) /*-{
        console.log(text);
    }-*/;
    
}
