package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BuyerWalletModel;
import lombok.*;

/**
 * Response DTO to endpoints response Buyer wallet
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyerWalletResponse {

    /**
     * Balance from wallet
     */
    private Double balance;

    /**
     * Buyer name
     */
    private String buyerName;

    public static BuyerWalletResponse toResponse(BuyerWalletModel buyerWalletModel) {
        return BuyerWalletResponse.builder()
                .balance(buyerWalletModel.getBalance())
                .buyerName(buyerWalletModel.getBuyer().getName())
                .build();
    }
}
