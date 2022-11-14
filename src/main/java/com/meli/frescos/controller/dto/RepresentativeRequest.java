package com.meli.frescos.controller.dto;

import com.meli.frescos.model.RepresentativeModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotBlank(message = "O nome do representante não deve estar em branco")
    private String name;

    /**
     * Representative warehouse id
     */
    @NotNull(message = "O id do warehouse não pode estar em branco")
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
