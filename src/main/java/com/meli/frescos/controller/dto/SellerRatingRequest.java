package com.meli.frescos.controller.dto;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerRatingRequest {

    /**
     * Seller id
     */
    @NotNull(message = "O id do vendedor deve ser informado")
    private Long sellerId;

    /**
     * Buyer id
     */
    @NotNull(message = "O id do comprador deve ser informado")
    private Long buyerId;

    /**
     * Purchase order id
     */
    @NotNull(message = "O id da ordem de compra deve ser informada")
    private Long purchaseOrderId;

    /**
     * Seller rating
     */
    @Digits(integer = 1, fraction = 2, message = "A avaliação do vendedor não pode ser maior que 5 e deve conter no máximo duas casas decimais")
    @Range(min = 0, max = 5, message = "A avaliação do vendedor deve ser de 0 à 5")
    private Double rating;

}
