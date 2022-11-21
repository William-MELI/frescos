package com.meli.frescos.exception;

/**
 * This exception is used when order product is invalid
 */
public class OrderProductIsInvalidException extends RuntimeException {
    public OrderProductIsInvalidException(String s) {
        super(s);
    }
}
