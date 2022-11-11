package com.meli.frescos.exception;

/**
 * This Exception is used when a PurchaseOrder can not be found is required operation by id
 */
public class PurchaseOrderByIdNotFoundException extends RuntimeException {

    public PurchaseOrderByIdNotFoundException(Long id) {
        super("PurchaseOrder com id " + id + " não encontrado");
    }
}