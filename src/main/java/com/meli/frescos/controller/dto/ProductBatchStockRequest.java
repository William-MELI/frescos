package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.ProductModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public List<BatchStockModel> toBatchStock() {
        List<BatchStockModel> batchStockList = new ArrayList<>();
        this.getInboundOrder().getBatchStock().forEach(b -> batchStockList.add(BatchStockModel.builder()
                        .batchNumber(b.getBatchNumber())
                        .quantity((b.getProductQuantity()))
                        .manufacturingDate(b.getManufacturingDate())
                        .manufacturingTime(b.getManufacturingDatetime())
                        .dueDate(b.getDueDate())
                .build()));
        return batchStockList;
    }
}
