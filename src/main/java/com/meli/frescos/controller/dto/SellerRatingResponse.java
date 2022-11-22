package com.meli.frescos.controller.dto;

import com.meli.frescos.model.SellerModel;
import com.meli.frescos.model.SellerRatingModel;
import lombok.*;

/**
 * DTO response to endpoints related to SellerRating
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerRatingResponse {

    /**
     * Seller name
     */
    private String sellerName;

    /**
     * Buyer name
     */
    private String buyerName;

    /**
     * Purchase Order Id
     */
    private Long purchaseOrderId;

    /**
     * Seller rating
     */
    private Double sellerRating;

    /**
     * Maps SellerRatingModel to SellerRatingResponse
     * @param sellerRating SellerRatingModel
     * @return SellerRatingResponse
     */
    public static SellerRatingResponse toResponse(SellerRatingModel sellerRating) {
        return SellerRatingResponse.builder()
                .sellerName(sellerRating.getSeller().getName())
                .buyerName(sellerRating.getBuyer().getName())
                .purchaseOrderId(sellerRating.getPurchaseOrder().getId())
                .sellerRating(sellerRating.getRating())
                .build();
    }
}
