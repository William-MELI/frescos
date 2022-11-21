package com.meli.frescos.exception;

/**
 * This Exception is used when a primary key is related to record from another table
 */
public class UsedPrimaryKeyConstraintException extends Throwable {
    public UsedPrimaryKeyConstraintException(String message) {
        super(message);
    }
}
