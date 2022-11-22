package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BuyerWalletModel;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyerWalletResponse {

    private Double balance;

    private String buyerName;

    public static BuyerWalletResponse toResponse(BuyerWalletModel buyerWalletModel) {
        return BuyerWalletResponse.builder()
                .balance(buyerWalletModel.getBalance())
                .buyerName(buyerWalletModel.getBuyer().getName())
                .build();
    }
}
