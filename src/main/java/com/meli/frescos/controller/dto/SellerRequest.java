package com.meli.frescos.controller.dto;

import com.meli.frescos.model.SellerModel;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SellerRequest {

    @NotBlank(message = "O nome do vendedor deve ser preenchido.")
    private String name;

    @NotBlank(message = "O CPF do vendedor deve ser preenchido.")
    @Size(min = 11, max = 11, message = "Preencher somente com números.")
    private String cpf;

    @Size(min = 0, max = 5)
    private Double rating;

    public SellerModel toModel(){
        return SellerModel.builder()
                .name(this.name)
                .cpf(this.cpf)
                .rating(this.rating)
                .build();
    }
}
