package com.meli.frescos.exception;

/**
 * This Exception is used when a Seller can not be found is required operation
 */
public class SellerByIdNotFoundException extends RuntimeException{

    public SellerByIdNotFoundException(Long id){
        super("Vendedor com id " + id + " não encontrado");
    }
}
