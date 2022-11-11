package com.meli.frescos.controller.dto;

import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.model.SellerModel;
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
public class ProductResponse {

    /**
     * Product id
     */
    private Long id;

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
     * Average product volume
     */
    private Double unitVolume;

    /**
     * Average product weight
     */
    private Double unitWeight;

    /**
     * Product creation date
     */
    private LocalDate createDate;

    /**
     * Seller id
     */
    private SellerModel seller;

    /**
     * Product total batch quantity
     */
    private Integer totalQuantity;

    /**
     * Maps ProductModel and Integer total batch quantity to ProductResponse
     * @param product ProductModel
     * @param totalQuantity Integer total batch quantity
     * @return ProductResponse
     */
    public static ProductResponse toResponse(ProductModel product, Integer totalQuantity) {
        return ProductResponse.builder()
                .id(product.getId())
                .productTitle(product.getProductTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .unitVolume(product.getUnitVolume())
                .unitWeight(product.getUnitWeight())
                .createDate(LocalDate.now())
                .seller(product.getSeller())
                .totalQuantity(totalQuantity)
                .build();
    }

}
