package com.meli.frescos.controller.dto;

import com.meli.frescos.model.WarehouseModel;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseResponse {

    String localization;

    public static WarehouseResponse toResponse(WarehouseModel warehouse){
        return WarehouseResponse.builder()
                .localization(warehouse.getLocalization())
                .build();
    }
}
