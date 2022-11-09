package com.meli.frescos.controller.dto;

import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.model.WarehouseModel;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Builder
@Getter
public class SectionRequest {

    @NotBlank(message = "A descrição não pode estar em branco")
    @Size(max = 100, message = "A descrição do Setor deve ter no máximo 100 caracteres")
    private String description;

    @NotBlank(message = "A categoria não pode estar em branco")
    @Positive(message = "A categoria deve ser um valor positivo entre 1 e 3")
    private CategoryEnum category;

    @Positive(message = "O valor do totalSize deve ser um valor positivo")
    private Double totalSize;

    @NotBlank(message = "A temperatura não pode estar em branco")
    private Double temperature;

    @NotBlank(message = "O id do Armazém não pode estar branco")
    @Positive(message = "O id do Armazém deve ser um valor positivo")
    private WarehouseModel warehouse;
}
