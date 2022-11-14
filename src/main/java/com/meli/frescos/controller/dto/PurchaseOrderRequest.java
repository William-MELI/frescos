package com.meli.frescos.controller.dto;

import com.meli.frescos.model.OrderStatusEnum;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * Request DTO for PurchaseOrder related endpoints
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderRequest {

    /**
     * PurchaseOrder creation date
     */
    @NotNull(message = "A data nao pode estar em branco")
    private LocalDate date;

    /**
     * Buyer id
     */
    @NotNull(message = "O comprador nao pode estar em branco")
    private Long buyer;

    /**
     * List of OrderProducts
     */
    @NotEmpty(message = "A lista de produtos nao pode estar vazia")
    private List<OrderProductsRequest> products;

    /**
     * Maps PurchaseOrderRequest to PurchaseOrderModel
     * @return PurchaseOrderModel
     */
    public PurchaseOrderRequest toModel() {
        return PurchaseOrderRequest.builder()
                .date(this.date)
                .buyer(this.buyer)
                .products(this.products)
                .build();
    }
}
