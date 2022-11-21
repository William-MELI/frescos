package com.meli.frescos.exception;

/**
 * This exception is used when a filter is made with an invalid parameter
 */
public class BatchStockFilterCategoryInvalidException extends RuntimeException{

    public BatchStockFilterCategoryInvalidException(String filter){
        super("O filtro do tipo " + filter.toUpperCase() + " é inválido. Os tipos aceitos são: FS (FRESH), RF (REFRIGERATED) ou FF (FROZEN)");
    }
}
