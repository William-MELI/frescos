package com.meli.frescos.controller.dto;

import com.meli.frescos.model.WarehouseModel;
import lombok.*;

/**
 * Response DTO for Warehouse related endpoints
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseResponse {

    /**
     * Warehouse id
     */
    private Long id;

    /**
     * Warehouse district
     */
    private String district;

    /**
     * Warehouse state
     */
    private String state;

    /**
     * Warehouse city
     */
    private String city;

    /**
     * Warehouse street
     */
    private String street;

    /**
     * Warehouse postalcode
     */
    private String postalCode;

    /**
     * Maps WarehouseModel to WarehouseResponse
     * @param warehouse WarehouseModel
     * @return BatchStockResponse
     */
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
