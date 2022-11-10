package com.meli.frescos.controller.dto;

import com.meli.frescos.model.RepresentativeModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepresentativeRequest {

    private String name;

    private Long warehouseCode;

    public RepresentativeModel toRepresentative() {
        return RepresentativeModel.builder()
                .name(this.name)
                .build();
    }
}
