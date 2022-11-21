package com.meli.frescos.exception;

/**
 * This exception is used when null due date in database
 */
public class NullDueDateException extends Exception {
    public NullDueDateException(String message) {
        super(message);
    }
}
