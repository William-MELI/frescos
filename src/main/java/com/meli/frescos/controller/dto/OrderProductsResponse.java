package com.meli.frescos.controller.dto;

import com.meli.frescos.model.OrderProductsModel;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.model.PurchaseOrderModel;
import lombok.*;

/**
 * Response DTO for PurchaseOrder related endpoints
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductsResponse {

    /**
     * OrderProduct id
     */
    private Long id;

    /**
     * Product id
     */
    private ProductModel productModel;

    /**
     * OrderProduct quantity
     */
    private int quantity;

    /**
     * PurchaseOrder id
     */
    private PurchaseOrderModel purchaseOrderModel;

    /**
     * Maps OrderProductsModel to OrderProductsResponse
     * @param order OrderProductModel
     * @return OrderProductsResponse
     */
    public static OrderProductsResponse toResponse(OrderProductsModel order) {
        return OrderProductsResponse.builder()
                .id(order.getId())
                .productModel(order.getProductModel())
                .quantity(order.getQuantity())
                .purchaseOrderModel(order.getPurchaseOrderModel())
                .build();
    }
}
