package com.meli.frescos.controller.dto;

import com.meli.frescos.model.Refrigerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefrigeratorRequest {

    private String brand;

    private String model;

    public Refrigerator toModel() {
        return Refrigerator.builder()
                .brand(this.brand)
                .model(this.model)
                .build();
    }

}
