package com.meli.frescos.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductsRequest {

    @NotNull(message = "O id do produto não pode estar em branco")
    private Long productModel;

    @NotNull(message = "A quantidade não pode estar em branco")
    @Positive(message = "O valor da quantidade deve ser um número positivo")
    private int quantity;
}
