package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.model.SectionModel;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchStockResponse {

    private Long id;

    private String batchNumber;

    private Double quantity;

    private LocalDate manufacturingDate;

    private LocalDateTime manufacturingTime;

    private LocalDate dueDate;

    private ProductModel product;

    private SectionModel section;

    public static BatchStockResponse toResponse(BatchStockModel batchStockModel) {

        return BatchStockResponse.builder()
                .id(batchStockModel.getId())
                .batchNumber(batchStockModel.getBatchNumber())
                .quantity(batchStockModel.getQuantity())
                .manufacturingDate(batchStockModel.getManufacturingDate())
                .manufacturingTime(batchStockModel.getManufacturingTime())
                .dueDate(batchStockModel.getDueDate())
                .product(batchStockModel.getProduct())
                .section(batchStockModel.getSection())
                .build();
    }
}
