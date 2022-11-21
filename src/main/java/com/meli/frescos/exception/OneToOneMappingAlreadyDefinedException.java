package com.meli.frescos.exception;

/**
 * This exception is used when there is already a storage one to one
 */
public class OneToOneMappingAlreadyDefinedException extends RuntimeException {
    public OneToOneMappingAlreadyDefinedException(String message) {
        super(message);
    }
}
