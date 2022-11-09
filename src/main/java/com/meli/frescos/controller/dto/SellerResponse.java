package com.meli.frescos.controller.dto;

import com.meli.frescos.model.SellerModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SellerResponse {

    private String name;
    private Double rating;

    public static SellerResponse toResponse(SellerModel seller){
        return SellerResponse.builder()
                .name(seller.getName())
                .rating(seller.getRating())
                .build();
    }
}
