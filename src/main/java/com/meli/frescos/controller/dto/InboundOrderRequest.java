package com.meli.frescos.controller.dto;

import com.meli.frescos.model.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private Long representativeCode;

    /**
     * Product title
     */
    @NotNull
    private String productTitle;

    /**
     * Product description
     */
    @NotBlank
    private String productDescription;

    /**
     * Average product volume
     */
    @NotNull
    private Double unitVolume;

    /**
     * Average product weight
     */
    @NotNull
    private Double unitWeight;

    /**
     * Product Unit price
     */
    @NotNull
    private BigDecimal price;

    /**
     * Product category - FRESH/FROZEN/REFRIGERATED
     */
    @NotNull
    private CategoryEnum category;

    /**
     * Inbound list of Batches
     */
    @NotEmpty
    private List<BatchStockRequest> batchStock;

}
