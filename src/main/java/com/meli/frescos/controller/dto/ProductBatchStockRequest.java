package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.model.SectionModel;
import com.meli.frescos.model.SellerModel;
import lombok.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Request DTO for ProductBatchStock POST
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductBatchStockRequest {

    /**
     * InboundOrder data
     */
    @Valid
    private InboundOrderRequest inboundOrder;

    /**
     * Maps ProductBatchStockRequest to ProductModel
     * @return ProductModel
     */
    public ProductModel toProduct() {
        return ProductModel.builder()
                .seller(SellerModel.builder().id(getInboundOrder().getSellerCode()).build())
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
                        .section(SectionModel.builder().id(b.getSectionCode()).build())
                        .batchNumber(b.getBatchNumber())
                        .quantity((b.getProductQuantity()))
                        .manufacturingDate(b.getManufacturingDate())
                        .manufacturingTime(b.getManufacturingDatetime())
                        .dueDate(b.getDueDate())
                .build()));
        return batchStockList;
    }
}
