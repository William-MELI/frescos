package com.meli.frescos.exception;

public class SellerByIdNotFoundException extends RuntimeException {

    public SellerByIdNotFoundException(Long id) {
        super("Vendedor com id " + id + " não encontrado");
    }
}
