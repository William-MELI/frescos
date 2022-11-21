package com.meli.frescos.exception;

/**
 * This exception is used when not enough stock
 */
public class NotEnoughStockException extends Exception {
    public NotEnoughStockException(String message) {
        super(message);
    }
}
