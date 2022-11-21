package com.meli.frescos.exception;

/**
 * This Exception is used when a Representative is not associated with a Warehouse
 */
public class RepresentativeWarehouseNotAssociatedException extends Exception {

    public RepresentativeWarehouseNotAssociatedException(String m){
        super(m);
    }
}