package com.meli.frescos.controller.dto;

import com.meli.frescos.model.SellerModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO response to endpoints related to Seller
 */
@Getter
@Setter
@Builder
public class SellerResponse {

    /**
     * Seller name
     */
    private String name;

    /**
     * Seller rating
     */
    private Double rating;

    public static SellerResponse toResponse(SellerModel seller){
        return SellerResponse.builder()
                .name(seller.getName())
                .rating(seller.getRating())
                .build();
    }
}
