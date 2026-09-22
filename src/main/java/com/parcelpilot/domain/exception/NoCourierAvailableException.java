package com.parcelpilot.domain.exception;

public class NoCourierAvailableException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NoCourierAvailableException(String message) { super(message); }
}
