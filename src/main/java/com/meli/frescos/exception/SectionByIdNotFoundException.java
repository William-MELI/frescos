package com.meli.frescos.exception;

/**
 * This Exception is used when a Section can not be found is required operation
 */
public class SectionByIdNotFoundException extends  RuntimeException {
    public SectionByIdNotFoundException(Long id) {
        super("O setor com o id " + id + " não foi encontrado.");
    }
}
