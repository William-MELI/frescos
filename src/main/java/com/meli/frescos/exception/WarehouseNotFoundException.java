package com.meli.frescos.exception;

/**
 * This Exception is used when a Warehouse can not be found is required operation
 */
public class WarehouseNotFoundException extends Exception {
    public WarehouseNotFoundException(String s) {
        super(s);
    }
}
