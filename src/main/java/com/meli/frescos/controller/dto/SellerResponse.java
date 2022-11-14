package com.meli.frescos.controller.dto;

import com.meli.frescos.model.SellerModel;
import lombok.*;

/**
 * DTO response to endpoints related to Seller
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerResponse {

    /**
     * Seller id
     */
    private Long id;

    /**
     * Seller name
     */
    private String name;

    /**
     * Seller rating
     */
    private Double rating;

    /**
     * Maps SellerModel to SellerResponse
     * @param seller SellerModel
     * @return SellerResponse
     */
    public static SellerResponse toResponse(SellerModel seller) {
        return SellerResponse.builder()
                .id(seller.getId())
                .name(seller.getName())
                .rating(seller.getRating())
                .build();
    }
}
