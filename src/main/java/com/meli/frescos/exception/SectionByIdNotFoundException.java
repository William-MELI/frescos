package com.meli.frescos.exception;

public class SectionByIdNotFoundException extends  RuntimeException {
    public SectionByIdNotFoundException(Long id) {
        super("O setor com o id" + id + "não foi encontrado.");
    }
}
