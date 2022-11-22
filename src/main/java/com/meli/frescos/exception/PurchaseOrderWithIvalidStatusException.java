package com.meli.frescos.exception;

/**
 * This Exception is used when a PurchaseOrder has invalid status
 */
public class PurchaseOrderWithIvalidStatusException extends RuntimeException {

    public PurchaseOrderWithIvalidStatusException(String msg) {
        super(msg);
    }
}