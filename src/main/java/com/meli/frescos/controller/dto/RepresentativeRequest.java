package com.meli.frescos.controller.dto;

import com.meli.frescos.model.RepresentativeModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request DTO for Representative related endpoints
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepresentativeRequest {

    /**
     * Representative name
     */
    private String name;

    /**
     * Representative warehouse id
     */
    private Long warehouseCode;

    /**
     * Maps RepresentativeRequest to RepresentativeModel
     * @return RepresentativeModel
     */
    public RepresentativeModel toRepresentative() {
        return RepresentativeModel.builder()
                .name(this.name)
                .build();
    }
}
