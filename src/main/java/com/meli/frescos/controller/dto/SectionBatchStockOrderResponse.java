package com.meli.frescos.controller.dto;

import com.meli.frescos.model.SectionModel;
import lombok.*;

/**
 * Response DTO for BatchStock related endpoints
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionBatchStockOrderResponse {

    /**
     * BatchStock sectionCode
     */
    private Long sectionCode;

    /**
     * BatchStock warehouseCode
     */
    private Long warehouseCode;

    /**
     * Maps SectionModel to SectionBatchStockOrderResponse
     * @param sectionModel SectionModel
     * @return SectionBatchStockOrderResponse
     */
    public static SectionBatchStockOrderResponse toResponse(SectionModel sectionModel) {
        return SectionBatchStockOrderResponse.builder()
                .sectionCode(sectionModel.getId())
                .warehouseCode(sectionModel.getWarehouse().getId())
                .build();
    }
}
