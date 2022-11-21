package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BatchStockModel;
import lombok.*;

/**
 * Response DTO for BatchStock related endpoints
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchStockOrderResponse {

    /**
     * BatchStock id
     */
    private Long id;

    /**
     * BatchStock batchNumber
     */
    private SectionBatchStockOrderResponse section;

    /**
     * BatchStock productId
     */
    private Long productId;

    /**
     * BatchStockOrderItemResponse batchStock
     */
    private BatchStockOrderItemResponse batchStock;


    /**
     * Maps BatchStockModel to BatchStockOrderResponse
     * @param batchStock BatchStockModel
     * @return BatchStockOrderResponse
     */
    public static BatchStockOrderResponse toResponse(BatchStockModel batchStock){
        return BatchStockOrderResponse.builder()
                .id(batchStock.getId())
                .section(SectionBatchStockOrderResponse.toResponse(batchStock.getSection()))
                .productId(batchStock.getProduct().getId())
                .batchStock(BatchStockOrderItemResponse.toResponse(batchStock)).build();
    }
}
