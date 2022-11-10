package com.meli.frescos.controller.dto;

import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.model.WarehouseModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SectionRequest {

    @NotBlank(message = "A descrição não pode estar em branco")
    @Size(max = 100, message = "A descrição do Setor deve ter no máximo 100 caracteres")
    private String description;

    @NotNull(message = "A categoria não pode estar em branco")
    private CategoryEnum category;

    @Positive(message = "O valor do totalSize deve ser um valor positivo")
    private Double totalSize;

    @NotNull(message = "A temperatura não pode estar em branco")
    private Double temperature;

    @NotNull(message = "O id do Armazém não pode estar branco")
    @Positive(message = "O id do Armazém deve ser um valor positivo")
    private Long warehouse;
}
