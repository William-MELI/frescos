package com.meli.frescos.controller.dto;

import com.meli.frescos.model.RepresentativeModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepresentativeResponse {

    private Long id;

    private String name;

    private WarehouseResponse warehouse;

    public static RepresentativeResponse toResponse(RepresentativeModel representative) {
        return RepresentativeResponse.builder()
                .id(representative.getId())
                .name(representative.getName())
                .warehouse(WarehouseResponse.toResponse(representative.getWarehouse()))
                .build();

    }
}
