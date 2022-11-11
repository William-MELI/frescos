package com.meli.frescos.controller.dto;

import com.meli.frescos.model.RepresentativeModel;
import lombok.*;

/**
 * Response DTO for Representative related endpoints
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepresentativeResponse {

    /**
     * Representative id
     */
    private Long id;

    /**
     * Representative name
     */
    private String name;

    /**
     * Representative warehouse
     */
    private WarehouseResponse warehouse;

    /**
     * Maps RepresentativeModel to RepresentativeResponse
     * @param representative RepresentativeModel
     * @return RepresentativeResponse
     */
    public static RepresentativeResponse toResponse(RepresentativeModel representative) {
        return RepresentativeResponse.builder()
                .id(representative.getId())
                .name(representative.getName())
                .warehouse(WarehouseResponse.toResponse(representative.getWarehouse()))
                .build();

    }
}
