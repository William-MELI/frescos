package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BatchStockModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimplifiedBatchStockResponse {

    private Long sectionId;

    private Integer productQuantity;

    public static SimplifiedBatchStockResponse toResponse(BatchStockModel batchStockModel) {
        return SimplifiedBatchStockResponse.builder()
                .sectionId(batchStockModel.getSection().getId())
                .productQuantity(batchStockModel.getQuantity())
                .build();
    }
}
