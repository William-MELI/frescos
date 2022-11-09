package com.meli.frescos.controller.dto;

import com.meli.frescos.model.WarehouseModel;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseResponse {

    private String district;

    private String state;

    private String city;

    private String street;

    private String postalCode;

    public static WarehouseResponse toResponse(WarehouseModel warehouse){
        return WarehouseResponse.builder()
                .city(warehouse.getCity())
                .state(warehouse.getState())
                .street(warehouse.getStreet())
                .postalCode(warehouse.getPostalCode())
                .district(warehouse.getDistrict())
                .build();
    }

    public static List<WarehouseResponse> toResponse(List<WarehouseModel> warehouse){

        List<WarehouseResponse> warehouseResponseList = warehouse.stream().map(w -> WarehouseResponse.toResponse(w)).collect(Collectors.toList());

        return warehouseResponseList;
    }
}
