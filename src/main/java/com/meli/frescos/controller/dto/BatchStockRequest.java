package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BatchStockModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BatchStockRequest {

    private String batchNumber;

    private Integer productQuantity;

    private LocalDate manufacturingDate;

    private LocalDateTime manufacturingDatetime;

    private LocalDate dueDate;

    public BatchStockModel toModel() {
        return BatchStockModel.builder()
                .batchNumber(this.batchNumber)
                .quantity(this.productQuantity)
                .manufacturingDate(this.manufacturingDate)
                .manufacturingTime(this.manufacturingDatetime)
                .dueDate(this.dueDate)
                .build();
    }
}
