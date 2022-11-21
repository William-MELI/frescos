package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BuyerModel;
import lombok.*;

/**
 * Response DTO for Buyer related endpoints
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyerResponse {

    /**
     * Seller name
     */
    private String name;

    /**
     * Maps BuyerModel to BuyerResponse
     * @param buyer BuyerModel
     * @return BuyerResponse
     */
    public static BuyerResponse toResponse(BuyerModel buyer) {
        return BuyerResponse.builder()
                .name(buyer.getName())
                .build();
    }
}
