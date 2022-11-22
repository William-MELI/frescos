package com.meli.frescos.exception;

public class BuyerWalletNotExistException extends  RuntimeException{
    public BuyerWalletNotExistException(Long id) {
        super("Buyer wallet com " + id + " nao existe.");
    };
}
