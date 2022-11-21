package com.meli.frescos.exception;

/**
 * This exception is used when a filter is made with an invalid parameter
 */
public class BatchStockFilterOrderInvalidException extends RuntimeException{

    public BatchStockFilterOrderInvalidException(String order){
        super("A ordenação do tipo " + order.toUpperCase() + " é inválida. Os tipos aceitos são: L (número do lote), Q (quantidade produto) ou V (data de vencimento produto)");
    }
}
