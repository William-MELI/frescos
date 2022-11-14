package com.meli.frescos.controller.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * Response DTO for PurchaseOrder POST
 */
@Getter
@Setter
@Builder
public class PurchaseOrderResponse {

    /**
     * PurchaseOrder totalprice;
     */
    private BigDecimal totalprice;

    /**
     * Maps PurchaseOrderModel to PurchaseOrderResponse
     * @param totalprice BigDecimal
     * @return PurchaseOrderResponse
     */
    public static PurchaseOrderResponse toResponse(BigDecimal totalprice) {
        return PurchaseOrderResponse.builder()
                .totalprice(totalprice)
                .build();

    }
}
