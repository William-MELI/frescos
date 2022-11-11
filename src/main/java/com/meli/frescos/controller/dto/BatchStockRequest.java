package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BatchStockModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Request DTO for BatchStock related endpoints
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BatchStockRequest {

    /**
     * Batch number of the inbound product
     */
    @NotBlank(message = "Número de Lote não pode estar em branco.")
    private String batchNumber;

    /**
     * Batch quantity of the inbound batch
     */
    @Positive(message = "A quantidade do lote deve ser positiva.")
    private Integer productQuantity;

    /**
     * Manufacturing date of the inbound batch
     */
    @PastOrPresent(message = "A data de fabricação pode ser um data futura.")
    private LocalDate manufacturingDate;

    /**
     * Manufacturing date and time of the inbound batch
     */
    @PastOrPresent(message = "A data de fabricação pode ser um data futura.")
    private LocalDateTime manufacturingDatetime;

    /**
     * Due date of the inbound batch
     */
    @Future(message = "A data de validade deve ser uma data futura.")
    private LocalDate dueDate;

    /**
     * Maps BatchStockRequest to BatchStockModel
     * @return BatchStockModel
     */
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
