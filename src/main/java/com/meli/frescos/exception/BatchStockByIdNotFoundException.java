package com.meli.frescos.exception;

public class BatchStockByIdNotFoundException extends RuntimeException{

    public BatchStockByIdNotFoundException(Long id) {
        super("BatchStock com o id:" + id + "nao encontrado.");
    }
}
