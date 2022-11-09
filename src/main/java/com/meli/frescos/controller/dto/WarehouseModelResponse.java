package com.meli.frescos.controller.dto;

import com.meli.frescos.model.WarehouseModel;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseModelResponse {

    String localization;

    public static WarehouseModelResponse toResponse(WarehouseModel warehouse){
        return WarehouseModelResponse.builder()
                .localization(warehouse.getLocalization())
                .build();
    }
}
