package com.meli.frescos.controller.dto;

import com.meli.frescos.model.ProductModel;
import lombok.*;

import java.util.List;

/**
 * Response DTO for Product GET
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailedResponse {

    /**
     * Product id
     */
    private Long productId;

    /**
     * Product total batch quantity
     */
    private List<SimplifiedBatchStockResponse> batchStock;

    /**
     * Maps ProductModel and Integer total batch quantity to ProductResponse
     * @param product ProductModel
     * @param batchStockList List BatchStockModel
     * @return ProductResponse
     */
    public static ProductDetailedResponse toResponse(ProductModel product, List<SimplifiedBatchStockResponse> batchStockList) {
        return ProductDetailedResponse.builder()
                .productId(product.getId())
                .batchStock(batchStockList)
                .build();
    }

}
