package com.meli.frescos.exception;

public class BuyerAlreadyHasWalletException extends RuntimeException{

    public BuyerAlreadyHasWalletException(Long id) {
        super("Buyer " + id + " já possue uma wallet associada");
    }
}
