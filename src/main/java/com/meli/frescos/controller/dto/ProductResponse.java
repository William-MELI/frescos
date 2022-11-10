package com.meli.frescos.controller.dto;

import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.model.SellerModel;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long id;

    private String productTitle;

    private String description;

    private BigDecimal price;

    private CategoryEnum category;

    private Double unitVolume;

    private Double unitWeight;

    private LocalDate createDate;

    private SellerModel seller;

    private Integer totalQuantity;

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
