package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BuyerWalletModel;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyerWalletRequest {

    @NotBlank
    private Double balance;

    @NotBlank
    private Long buyerId;

    public BuyerWalletModel toModel() {

        return BuyerWalletModel.builder()
                .balance(this.balance)
                .build();
    }
}
