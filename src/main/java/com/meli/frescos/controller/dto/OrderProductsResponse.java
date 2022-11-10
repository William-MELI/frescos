package com.meli.frescos.controller.dto;

import com.meli.frescos.model.OrderProductsModel;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.model.PurchaseOrderModel;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductsResponse {
    private Long id;

    private ProductModel productModel;

    private int quantity;

    private PurchaseOrderModel purchaseOrderModel;

    public static OrderProductsResponse toResponse(OrderProductsModel order) {
        return OrderProductsResponse.builder()
                .id(order.getId())
                .productModel(order.getProductModel())
                .quantity(order.getQuantity())
                .purchaseOrderModel(order.getPurchaseOrderModel())
                .build();
    }
}
