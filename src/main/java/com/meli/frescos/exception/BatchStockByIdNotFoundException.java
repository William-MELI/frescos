package com.meli.frescos.exception;

/**
 * This Exception is used when a BatchStock can not be found is required operation
 */
public class BatchStockByIdNotFoundException extends RuntimeException {

    public BatchStockByIdNotFoundException(Long id) {
        super("BatchStock com o id:" + id + "nao encontrado.");
    }
}
