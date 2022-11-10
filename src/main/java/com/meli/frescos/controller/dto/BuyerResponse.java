package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.model.SellerModel;
import lombok.*;

/**
 * DTO response to endpoints related to Buyer
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyerResponse {

    /**
     * Seller id
     */
    private Long id;

    /**
     * Seller name
     */
    private String name;

    public static BuyerResponse toResponse(BuyerModel buyer) {
        return BuyerResponse.builder()
                .id(buyer.getId())
                .name(buyer.getName())
                .build();
    }
}
