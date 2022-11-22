package com.meli.frescos.controller.dto;

import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.model.ProductModel;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Response DTO for Product GET
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductContainDescriptionResponse {

    /**
     * Product title
     */
    private String productTitle;

    /**
     * Product description
     */
    private String description;

    /**
     * Product price
     */
    private BigDecimal price;

    /**
     * Product category - FRESH/FROZEN/REFRIGERATED
     */
    private CategoryEnum category;

    /**
     * Seller name
     */
    private String nameSeller;

    /**
     * Rating seller
     */
    private Double ratingSeller;

    /**
     * Product closest due date
     */
    private LocalDate closestDueDate;

    /**
     * Maps ProductModel and closest due date to ProductResponse
     * @param product ProductModel
     * @param closestDueDate Closest due date
     * @return ProductResponse
     */
    public static ProductContainDescriptionResponse toResponse(ProductModel product, LocalDate closestDueDate) {
        return ProductContainDescriptionResponse.builder()
                .productTitle(product.getProductTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .nameSeller(product.getSeller().getName())
                .ratingSeller(product.getSeller().getRating())
                .closestDueDate(closestDueDate)
                .build();
    }

}
