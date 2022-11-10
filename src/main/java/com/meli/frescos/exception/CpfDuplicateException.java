package com.meli.frescos.exception;

/**
 * This Exception is used when a Seller is saved with a cpf that already exists
 */
public class CpfDuplicateException extends RuntimeException{

    public CpfDuplicateException(String cpf) {
        super("CPF " + cpf + " já registrado.");
    }
}
