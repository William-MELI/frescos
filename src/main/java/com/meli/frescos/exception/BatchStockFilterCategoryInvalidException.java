package com.meli.frescos.exception;

/**
 * This exception is used when a filter is made with order parameter different from
 */
public class BatchStockFilterCategoryInvalidException extends RuntimeException{

    public BatchStockFilterCategoryInvalidException(String filter){
        super("O filtro do tipo " + filter.toUpperCase() + " é inválido. Os tipos aceitos são: FS (FRESH), RF (REFRIGERATED) ou FF (FROZEN)");
    }
}
