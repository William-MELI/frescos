package com.meli.frescos.controller.dto;

import com.meli.frescos.model.WarehouseModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseResponse {

    private Long id;

    private String district;

    private String state;

    private String city;

    private String street;

    private String postalCode;

    public static WarehouseResponse toResponse(WarehouseModel warehouse) {
        return WarehouseResponse.builder()
                .id(warehouse.getId())
                .city(warehouse.getCity())
                .state(warehouse.getState())
                .street(warehouse.getStreet())
                .postalCode(warehouse.getPostalCode())
                .district(warehouse.getDistrict())
                .build();
    }

}
