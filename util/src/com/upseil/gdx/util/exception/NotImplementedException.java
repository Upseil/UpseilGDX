package com.upseil.gdx.util.exception;

public class NotImplementedException extends RuntimeException {
    private static final long serialVersionUID = 5019135266927265412L;

    public NotImplementedException() {
        super();
    }

    public NotImplementedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotImplementedException(String message) {
        super(message);
    }

    public NotImplementedException(Throwable cause) {
        super(cause);
    }
    
}
