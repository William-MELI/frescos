package com.meli.frescos.exception;

/**
 * This exception is used when not enough space in section
 */
public class NotEnoughSpaceInSectionException extends Exception {
    public NotEnoughSpaceInSectionException(String message) {
        super(message);
    }
}
