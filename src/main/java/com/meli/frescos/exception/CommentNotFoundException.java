package com.meli.frescos.exception;

/**
 * This exception is used when a BuyerModel can not be found given operation
 */
public class CommentNotFoundException extends Exception {

    public CommentNotFoundException(String message) {
        super(message);
    }
}
