package com.parcelpilot.domain.exception;

public class InvalidDeliveryStateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidDeliveryStateException(String message) { super(message); }
}
