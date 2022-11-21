package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BatchStockModel;
import lombok.*;

import java.time.LocalDate;

/**
 * Response DTO for BatchStock related endpoints
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchStockOrderItemResponse {

    /**
     * BatchStock batchNumber
     */
    private String batchNumber;

    /**
     * BatchStock currentQuantity
     */
    private Integer currentQuantity;

    /**
     * BatchStock dueDate
     */
    private LocalDate dueDate;

    /**
     * Maps BatchStockModel to BatchStockOrderItemResponse
     * @param batchStock BatchStockModel
     * @return BatchStockOrderItemResponse
     */
    public static BatchStockOrderItemResponse toResponse(BatchStockModel batchStock){
        return BatchStockOrderItemResponse.builder()
                .batchNumber(batchStock.getBatchNumber())
                .currentQuantity(batchStock.getQuantity())
                .dueDate(batchStock.getDueDate()).build();
    }
}
