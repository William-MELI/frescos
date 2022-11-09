package com.meli.frescos.controller.dto;

import com.meli.frescos.model.WarehouseModel;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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

    public static List<WarehouseResponse> toResponse(List<WarehouseModel> warehouse){

        List<WarehouseResponse> warehouseResponseList = warehouse.stream().map(w -> WarehouseResponse.toResponse(w)).collect(Collectors.toList());

        return warehouseResponseList;
    }
}
