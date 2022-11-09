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
     * BatchStockModel quantity
     */
    private Double quantity;

    /**
     * BatchStockModel manufacturing date
     */
    private LocalDate manufacturingDate;

    /**
     * BatchStockModel manufacturing date and time
     */
    private LocalDateTime manufacturingTime;

    /**
     * BatchStockModel due date
     */
    private LocalDate dueDate;

    /**
     * Product related to BatchStockModel
     */
    private ProductModel product;

    /**
     * Section related to BatchStockModel
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
