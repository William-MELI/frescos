package com.meli.frescos.controller.dto;

import com.meli.frescos.model.WarehouseModel;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Getter
public class WarehouseRequest {

    @NotBlank(message = "A localização não pode ser vazia")
    @Size(min = 3, message = "A localização precisa, ter no mínimo, três caracteres")
    String localization;

    public WarehouseModel toEntity(){
        return new WarehouseModel(this.localization);
    }
}
