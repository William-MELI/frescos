package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BuyerModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Request DTO for Buyer related endpoints
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BuyerRequest {

    /**
     * Buyer name
     */
    @NotBlank(message = "O nome do comprador deve ser preenchido.")
    private String name;

    /**
     * Buyer cpf
     */
    @NotBlank(message = "O CPF do comprador deve ser preenchido.")
    @CPF
    private String cpf;

    /**
     * Maps BuyerRequest to BuyerModel
     * @return BuyerModel
     */
    public BuyerModel toModel() {

        return BuyerModel.builder()
                .name(this.name)
                .cpf(this.cpf)
                .build();
    }
}
