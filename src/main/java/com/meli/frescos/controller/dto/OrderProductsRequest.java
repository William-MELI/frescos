package com.meli.frescos.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductsRequest {

    private Long productModel;

    @Positive(message = "O valor da quantidade deve ser um número positivo")
    private int quantity;

    private Long purchaseOrderModel;
}