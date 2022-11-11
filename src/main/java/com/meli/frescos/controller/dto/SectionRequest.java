package com.meli.frescos.controller.dto;

import com.meli.frescos.model.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * Request DTO for Section endpoints
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SectionRequest {

    /**
     * Section description
     */
    @NotBlank(message = "A descrição não pode estar em branco")
    @Size(max = 100, message = "A descrição do Setor deve ter no máximo 100 caracteres")
    private String description;

    /**
     * Section category - FRESH/FROZEN/REFRIGERATED
     */
    @NotNull(message = "A categoria não pode estar em branco")
    private CategoryEnum category;

    /**
     * Section size
     */
    @Positive(message = "O valor do totalSize deve ser um valor positivo")
    private Double totalSize;

    /**
     * Section temperature
     */
    @NotNull(message = "A temperatura não pode estar em branco")
    private Double temperature;

    /**
     * Section warehouse id
     */
    @NotNull(message = "O id do Armazém não pode estar branco")
    @Positive(message = "O id do Armazém deve ser um valor positivo")
    private Long warehouse;
}
