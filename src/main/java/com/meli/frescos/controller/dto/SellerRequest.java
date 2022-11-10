package com.meli.frescos.controller.dto;

import com.meli.frescos.model.SellerModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * DTO request to endpoints related to Seller
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SellerRequest {

    /**
     * Seller name
     */
    @NotBlank(message = "O nome do vendedor deve ser preenchido.")
    private String name;

    /**
     * Seller cpf
     */
    @NotBlank(message = "O CPF do vendedor deve ser preenchido.")
    @CPF(message = "CPF inválido")
    private String cpf;

    /**
     * Seller rating
     */
    @Digits(integer = 1, fraction = 2, message = "A avaliação do vendedor não pode ser maior que 5 e deve conter no máximo duas casas decimais")
    @Range(min = 0, max = 5, message = "A avaliação do vendedor deve ser de 0 à 5")
    private Double rating;

    public SellerModel toModel() {

        return SellerModel.builder()
                .name(this.name)
                .cpf(this.cpf)
                .rating(this.rating)
                .build();
    }
}
