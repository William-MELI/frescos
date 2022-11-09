package com.meli.frescos.controller.dto;

import com.meli.frescos.model.SellerModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SellerRequest {

    @NotBlank(message = "O nome do vendedor deve ser preenchido.")
    private String name;


    @NotBlank(message = "O CPF do vendedor deve ser preenchido.")
    @Size(min = 11, max = 11, message = "Preencher somente com números.")
    private String cpf;

    @Size(min = 0, max = 5)
    private Double rating;

    public SellerModel toEntity(){
        return new SellerModel(name, cpf, rating);
    }
}
