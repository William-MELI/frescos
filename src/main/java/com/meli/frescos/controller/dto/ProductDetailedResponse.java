package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.model.SellerModel;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Response DTO for Product GET
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailedResponse {

    /**
     * Product id
     */
    private Long id;

    /**
     * Product title
     */
    private String productTitle;

    /**
     * Product description
     */
    private String description;

    /**
     * Product price
     */
    private BigDecimal price;

    /**
     * Product category - FRESH/FROZEN/REFRIGERATED
     */
    private CategoryEnum category;

    /**
     * Average product volume
     */
    private Double unitVolume;

    /**
     * Average product weight
     */
    private Double unitWeight;

    /**
     * Product creation date
     */
    private LocalDate createDate;

    /**
     * Seller id
     */
    private SellerModel seller;

    /**
     * Product total batch quantity
     */
    private List<BatchStockResponse> batchStock;

    /**
     * Maps ProductModel and Integer total batch quantity to ProductResponse
     * @param product ProductModel
     * @param batchStockList List BatchStockModel
     * @return ProductResponse
     */
    public static ProductDetailedResponse toResponse(ProductModel product, List<BatchStockModel> batchStockList) {
        List<BatchStockResponse> batchStockResponseList = new ArrayList<>();
        batchStockList.forEach(b -> batchStockResponseList.add(BatchStockResponse.toResponse(b)));
        return ProductDetailedResponse.builder()
                .id(product.getId())
                .productTitle(product.getProductTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .unitVolume(product.getUnitVolume())
                .unitWeight(product.getUnitWeight())
                .createDate(LocalDate.now())
                .seller(product.getSeller())
                .batchStock(batchStockResponseList)
                .build();
    }

}
