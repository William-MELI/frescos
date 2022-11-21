package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.CategoryEnum;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchStockFiltersResponse {

    private String batchNumber;
    private Long productId;
    private CategoryEnum productTypeId;
    private LocalDate dueDate;
    private int quantity;

    public static BatchStockFiltersResponse toResponse(BatchStockModel batchStock){
        return BatchStockFiltersResponse.builder()
                .batchNumber(batchStock.getBatchNumber())
                .productId(batchStock.getProduct().getId())
                .productTypeId(batchStock.getProduct().getCategory())
                .dueDate(batchStock.getDueDate())
                .quantity(batchStock.getQuantity()).build();
    }

}
