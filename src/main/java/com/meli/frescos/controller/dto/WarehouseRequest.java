package com.meli.frescos.controller.dto;

import com.meli.frescos.model.WarehouseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Request DTO for Warehouse related endpoints
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseRequest {

    /**
     * Warehouse district
     */
    @NotBlank(message = "O bairro não pode ser vazio")
    @Size(min = 3, message = "O bairro precisa, ter no mínimo, três caracteres")
    private String district;

    /**
     * Warehouse state
     */
    @NotBlank(message = "O estado não pode ser vazio")
    @Size(min = 3, message = "O estado precisa, ter no mínimo, três caracteres")
    private String state;

    /**
     * Warehouse city
     */
    @NotBlank(message = "A cidade não pode ser vazia")
    @Size(min = 3, message = "A cidade precisa, ter no mínimo, três caracteres")
    private String city;

    /**
     * Warehouse street
     */
    @NotBlank(message = "A rua não pode ser vazia")
    @Size(min = 3, message = "A rua precisa, ter no mínimo, três caracteres")
    private String street;

    /**
     * Warehouse postalcode
     */
    @NotBlank(message = "O CEP não pode ser vazia")
    @Size(min = 8, max = 8, message = "O CEP precisa ter oito caracteres")
    private String postalCode;

    /**
     * Maps WarehouseRequest to WarehouseModel
     * @return WarehouseModel
     */
    public WarehouseModel toModel() {
        return WarehouseModel.builder()
                .city(this.city)
                .state(this.state)
                .street(this.street)
                .postalCode(this.postalCode)
                .district(this.district)
                .build();
    }
}
