package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.model.PurchaseOrderModel;
import com.meli.frescos.model.RepresentativeModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class PurchaseOrderResponse {


    private LocalDate date;

    private String orderStatus;

    private BuyerModel buyer;

    public static PurchaseOrderResponse toResponse(PurchaseOrderModel purchaseOrderModel) {
        return PurchaseOrderResponse.builder()
                .date(purchaseOrderModel.getDate())
                .orderStatus(purchaseOrderModel.getOrderStatus())
                .buyer(purchaseOrderModel.getBuyer())
                .build();

    }
}
