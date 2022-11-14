package com.meli.frescos.exception;

public class OneToOneMappingAlreadyDefinedException extends RuntimeException {
    public OneToOneMappingAlreadyDefinedException(String message) {
        super(message);
    }
}
