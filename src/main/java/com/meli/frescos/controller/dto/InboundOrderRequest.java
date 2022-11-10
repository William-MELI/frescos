package com.meli.frescos.controller.dto;

import com.meli.frescos.model.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InboundOrderRequest {

    private Long sectionCode;

    private Long warehouseCode;

    private Long sellerCode;

    private Long representativeCode;

    private String productTitle;

    private String productDescription;

    private Double unitVolume;

    private Double unitWeight;

    private BigDecimal price;

    private CategoryEnum category;

    private List<BatchStockRequest> batchStock;

}
