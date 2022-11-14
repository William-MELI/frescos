package com.meli.frescos.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Request DTO for PurchaseOrder related endpoints
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductsRequest {

    /**
     * Product id
     */
    @NotNull(message = "O id do produto não pode estar vazio")
    private Long productModel;

    /**
     * OrderProduct quantity
     */
    @Positive(message = "O valor da quantidade deve ser um número positivo")
    private int quantity;

    /**
     * PurchaseOrder id
     */
    @NotNull(message = "O id Purchase não pode estar vazio")
    private Long purchaseOrderModel;

}
