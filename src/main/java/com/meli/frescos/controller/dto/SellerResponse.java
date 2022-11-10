package com.meli.frescos.controller.dto;

import com.meli.frescos.model.SellerModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerResponse {

    private Long id;

    private String name;

    private Double rating;

    public static SellerResponse toResponse(SellerModel seller) {
        return SellerResponse.builder()
                .id(seller.getId())
                .name(seller.getName())
                .rating(seller.getRating())
                .build();
    }
}
