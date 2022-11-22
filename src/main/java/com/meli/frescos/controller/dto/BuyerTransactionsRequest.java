package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BuyerTransactionsModel;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Response DTO to endpoints request Buyer transactions
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyerTransactionsRequest {

    /**
     * value of transaction
     */
    @NotNull
    @Positive
    private Double value;

    public BuyerTransactionsModel toModel() {
        return BuyerTransactionsModel.builder()
                .value(this.value)
                .build();
    }
}
