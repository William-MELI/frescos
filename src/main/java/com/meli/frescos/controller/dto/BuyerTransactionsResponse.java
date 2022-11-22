package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BuyerTransactionsModel;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Response DTO to endpoints response to Buyer transactions
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyerTransactionsResponse {

    /**
     * LocalDateTime of transaction
     */
    private LocalDateTime date;

    /**
     * Value of transaction
     */
    private Double value;

    /**
     * Type of transaction
     */
    private String typeOfTransaction;

    public static BuyerTransactionsResponse toResponse(BuyerTransactionsModel buyerTransactionsModel) {
        return BuyerTransactionsResponse.builder()
                .date(buyerTransactionsModel.getDate())
                .typeOfTransaction(buyerTransactionsModel.getTypeOfTransaction())
                .value(buyerTransactionsModel.getValue())
                .build();
    }


}
