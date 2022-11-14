package com.meli.frescos.controller.dto;

import com.meli.frescos.model.SectionModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionBatchStockOrderResponse {
    private Long sectionCode;
    private Long warehouseCode;

    public static SectionBatchStockOrderResponse toResponse(SectionModel sectionModel) {
        return SectionBatchStockOrderResponse.builder()
                .sectionCode(sectionModel.getId())
                .warehouseCode(sectionModel.getWarehouse().getId())
                .build();
    }
}
