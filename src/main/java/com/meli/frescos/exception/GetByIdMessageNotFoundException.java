package com.meli.frescos.exception;

/**
 * This Exception is used when a MessageModel can not be found is required operation
 */
public class GetByIdMessageNotFoundException extends RuntimeException {
    public GetByIdMessageNotFoundException(Long id) { super("Mensagem com o id: " + id + " nao encontrada."); }
}
