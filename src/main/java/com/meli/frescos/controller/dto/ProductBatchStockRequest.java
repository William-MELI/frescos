package com.meli.frescos.controller.dto;

import com.meli.frescos.model.ProductModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductBatchStockRequest {

    private InboundOrderRequest inboundOrder;

    public ProductModel toProduct() {
        return ProductModel.builder()
                .productTitle(inboundOrder.getProductTitle())
                .description(inboundOrder.getProductDescription())
                .price(inboundOrder.getPrice())
                .category(inboundOrder.getCategory())
                .unitVolume(inboundOrder.getUnitVolume())
                .unitWeight(inboundOrder.getUnitWeight())
                .createDate(LocalDate.now())
                .build();
    }
}
