package com.meli.frescos.controller.dto;

import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.model.SectionModel;
import lombok.*;

/**
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionResponse {

    private Long id;

    private String description;

    private CategoryEnum category;

    private Double totalSize;

    private Double temperature;

    private WarehouseResponse warehouse;

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
