package com.meli.frescos.controller.dto;

import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.model.SectionModel;
import lombok.*;

/**
 * Response DTO for Section endpoints
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionResponse {

    /**
     * Section id
     */
    private Long id;

    /**
     * Section description
     */
    private String description;

    /**
     * Section Enum category
     */
    private CategoryEnum category;

    /**
     * Section totalSize
     */
    private Double totalSize;

    /**
     * Section temperature
     */
    private Double temperature;

    /**
     * Warehouse id
     */
    private WarehouseResponse warehouse;

    /**
     * Maps SectionModel to SectionResponse
     * @param section SectionModel
     * @return SectionResponse
     */
    public static SectionResponse toResponse(SectionModel section) {
        return SectionResponse.builder()
                .id(section.getId())
                .description(section.getDescription())
                .category(section.getCategory())
                .totalSize(section.getTotalSize())
                .temperature(section.getTemperature())
                .warehouse(WarehouseResponse.toResponse(section.getWarehouse()))
                .build();
    }

}
