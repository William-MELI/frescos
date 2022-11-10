package com.meli.frescos.exception;

/**
 * This exception is used when a BuyerModel can not be found given operation
 */
public class BuyerNotFoundException extends RuntimeException {

    public BuyerNotFoundException(String message) {
        super(message);
    }
}
