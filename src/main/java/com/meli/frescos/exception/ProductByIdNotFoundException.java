package com.meli.frescos.exception;

/**
 * This Exception is used when a Product can not be found is required operation
 */
public class ProductByIdNotFoundException extends RuntimeException {

    public ProductByIdNotFoundException(Long id) {
        super("Produto com id " + id + " não encontrado");
    }
}
