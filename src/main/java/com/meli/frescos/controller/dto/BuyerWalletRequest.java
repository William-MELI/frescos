package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BuyerWalletModel;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * Response DTO to endpoints request Buyer wallet
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyerWalletRequest {

    /**
     * Balance from wallet
     */
    @NotBlank
    @Positive
    private Double balance;

    /**
     * Id from buyer id
     */
    @NotBlank
    private Long buyerId;

    public BuyerWalletModel toModel() {

        return BuyerWalletModel.builder()
                .balance(this.balance)
                .build();
    }
}
