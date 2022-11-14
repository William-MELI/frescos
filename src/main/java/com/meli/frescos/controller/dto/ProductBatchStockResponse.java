package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.ProductModel;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Response DTO for ProductBatchStock POST
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductBatchStockResponse {

    /**
     * Product id
     */
    private Long productId;

    /**
     * Average product volume
     */
    private Double unitVolume;

    /**
     * Average product weight
     */
    private Double unitWeight;

    /**
     * Product Unit price
     */
    private BigDecimal price;

    /**
     * List of Product batches
     */
    List<BatchStockResponse> batchStock;

    /**
     * Maps ProductModel to ProductBatchStockResponse
     * @param product ProductModel
     * @return ProductBatchStockResponse
     */
    public static ProductBatchStockResponse toResponse(ProductModel product, List<BatchStockModel> batchStockList) {
        List<BatchStockResponse> batchStockResponseList = new ArrayList<>();
        batchStockList.forEach(b -> {
            batchStockResponseList.add(BatchStockResponse.builder()
                            .id(b.getId())
                            .batchNumber(b.getBatchNumber())
                            .currentTemperature(b.getSection().getTemperature())
                            .productQuantity(b.getQuantity())
                            .manufacturingDate(b.getManufacturingDate())
                            .manufacturingTime(b.getManufacturingTime())
                            .dueDate(b.getDueDate())
                    .build());
        });
        return ProductBatchStockResponse.builder()
                .productId(product.getId())
                .unitVolume(product.getUnitVolume())
                .unitWeight(product.getUnitWeight())
                .price(product.getPrice())
                .batchStock(batchStockResponseList)
                .build();
    }


}
