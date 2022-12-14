package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BatchStockModel;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Response DTO for BatchStock related endpoints
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchStockResponse {

    /**
     * BatchStock id
     */
    private Long id;

    /**
     * BatchStockModel number
     */
    private String batchNumber;

    /**
     * Current section temperature
     */
    private Double currentTemperature;

    /**
     * BatchStockModel quantity
     */
    private Integer productQuantity;

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
     * Maps BatchStockModel to BatchStockResponse
     * @param batchStockModel BatchStockModel
     * @return BatchStockResponse
     */
    public static BatchStockResponse toResponse(BatchStockModel batchStockModel) {

        return BatchStockResponse.builder()
                .id(batchStockModel.getId())
                .batchNumber(batchStockModel.getBatchNumber())
                .currentTemperature(batchStockModel.getSection().getTemperature())
                .productQuantity(batchStockModel.getQuantity())
                .manufacturingDate(batchStockModel.getManufacturingDate())
                .manufacturingTime(batchStockModel.getManufacturingTime())
                .dueDate(batchStockModel.getDueDate())
                .build();
    }
}
