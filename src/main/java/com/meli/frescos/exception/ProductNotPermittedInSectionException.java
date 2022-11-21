package com.meli.frescos.exception;

/**
 * This exception is used when product not permitted in section
 */
public class ProductNotPermittedInSectionException extends Exception {
    public ProductNotPermittedInSectionException(String message) {
        super(message);
    }
}
