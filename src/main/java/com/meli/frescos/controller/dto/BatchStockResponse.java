package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.model.SectionModel;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO response to endpoints related to BatchStock
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchStockResponse {

    /**
     * BatchStockModel id
     */
    private Long id;

    /**
     * BatchStockModel number
     */
    private String batchNumber;

    /**
     * BatchStock quantity
     */
    private Double quantity;

    /**
     * BatchStock manufacturing date
     */
    private LocalDate manufacturingDate;

    /**
     * BatchStock manufacturing date and time
     */
    private LocalDateTime manufacturingTime;

    /**
     * BatchStock due date
     */
    private LocalDate dueDate;

    /**
     * Product related to BatchStock
     */
    private ProductModel product;

    /**
     * Section related to BatchStock
     */
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
