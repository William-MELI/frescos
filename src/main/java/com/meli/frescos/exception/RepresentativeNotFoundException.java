package com.meli.frescos.exception;

/**
 * This Exception is used when a Representative can not be found is required operation
 */
public class RepresentativeNotFoundException extends Exception {
    public RepresentativeNotFoundException(String s) {
        super(s);
    }
}
