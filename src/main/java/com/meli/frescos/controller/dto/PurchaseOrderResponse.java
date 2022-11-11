package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.model.PurchaseOrderModel;
import com.meli.frescos.model.RepresentativeModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class PurchaseOrderResponse {


    private BigDecimal totalprice;


    public static PurchaseOrderResponse toResponse(BigDecimal totalprice) {
        return PurchaseOrderResponse.builder()
                .totalprice(totalprice)
                .build();

    }
}
