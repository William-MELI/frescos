package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BatchStockModel;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchStockOrderItemResponse {

    private String batchNumber;
    private Integer currentQuantity;
    private LocalDate dueDate;

    public static BatchStockOrderItemResponse toResponse(BatchStockModel batchStock){
        return BatchStockOrderItemResponse.builder()
                .batchNumber(batchStock.getBatchNumber())
                .currentQuantity(batchStock.getQuantity())
                .dueDate(batchStock.getDueDate()).build();
    }
}
