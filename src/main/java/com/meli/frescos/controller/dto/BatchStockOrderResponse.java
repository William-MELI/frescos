package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BatchStockModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchStockOrderResponse {
    private SectionBatchStockOrderResponse section;
    private Long productId;
    private BatchStockOrderItemResponse batchStock;

    public static BatchStockOrderResponse toResponse(BatchStockModel batchStock){
        return BatchStockOrderResponse.builder()
                .section(SectionBatchStockOrderResponse.toResponse(batchStock.getSection()))
                .productId(batchStock.getProduct().getId())
                .batchStock(BatchStockOrderItemResponse.toResponse(batchStock)).build();
    }
}
