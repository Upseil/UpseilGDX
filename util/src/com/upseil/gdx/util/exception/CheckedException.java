package com.upseil.gdx.util.exception;

public class CheckedException extends RuntimeException {
    private static final long serialVersionUID = 7670786984397457587L;

    public CheckedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckedException(Throwable cause) {
        super(cause);
    }
    
}
