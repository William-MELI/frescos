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
public class SimplifiedBatchStockResponse {

    /**
     * BatchStock sectionId
     */
    private Long sectionId;

    /**
     * BatchStock productQuantity
     */
    private Integer productQuantity;

    /**
     * Maps BatchStockModel to SimplifiedBatchStockResponse
     * @param batchStockModel BatchStockModel
     * @return SimplifiedBatchStockResponse
     */
    public static SimplifiedBatchStockResponse toResponse(BatchStockModel batchStockModel) {
        return SimplifiedBatchStockResponse.builder()
                .sectionId(batchStockModel.getSection().getId())
                .productQuantity(batchStockModel.getQuantity())
                .build();
    }
}
