package com.meli.frescos.controller.dto;

import com.meli.frescos.model.ProductModel;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductBatchStockResponse {

    private Long productId;

    private Double unitVolume;

    private Double unitWeight;

    private BigDecimal price;

    List<BatchStockResponse> batchStock;

    public static ProductBatchStockResponse toResponse(ProductModel product) {
        return ProductBatchStockResponse.builder()
                .productId(product.getId())
                .unitVolume(product.getUnitVolume())
                .unitWeight(product.getUnitWeight())
                .price(product.getPrice())
                .build();
    }


}
