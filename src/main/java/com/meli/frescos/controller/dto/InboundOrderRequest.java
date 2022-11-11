package com.meli.frescos.controller.dto;

import com.meli.frescos.model.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Request DTO for ProductBatchStock POST
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InboundOrderRequest {

    /**
     * Inbound section code
     */
    private Long sectionCode;

    /**
     * Inbound warehouse code
     */
    private Long warehouseCode;

    /**
     * Inbound Order seller code
     */
    private Long sellerCode;

    /**
     * Inbound representative code
     */
    private Long representativeCode;

    /**
     * Product title
     */
    private String productTitle;

    /**
     * Product description
     */
    private String productDescription;

    /**
     * Average product volume
     */
    private Double unitVolume;

    /**
     * Average product weight
     */
    private Double unitWeight;

    /**
     * Product Unit price
     */
    private BigDecimal price;

    /**
     * Product category - FRESH/FROZEN/REFRIGERATED
     */
    private CategoryEnum category;

    /**
     * Inbound list of Batches
     */
    private List<BatchStockRequest> batchStock;

}
