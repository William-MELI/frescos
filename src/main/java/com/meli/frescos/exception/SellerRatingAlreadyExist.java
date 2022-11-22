package com.meli.frescos.exception;

/**
 * This Exception is used when a SellerRating already exist
 */
public class SellerRatingAlreadyExist extends RuntimeException {

    public SellerRatingAlreadyExist(String msg) {
        super(msg);
    }
}