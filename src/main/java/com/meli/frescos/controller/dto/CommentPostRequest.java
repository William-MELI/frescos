package com.meli.frescos.controller.dto;

import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.model.CommentModel;
import com.meli.frescos.model.ProductModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Request DTO for Section endpoints
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentPostRequest {


    /**
     * Commentary
     */
    @NotBlank(message = "O comentário não pode estar em branco")
    @Size(max = 255, message = "O comentário deve ter no máximo 255 caracteres")
    private String comment;

    /**
     * Commentary created date
     */
    @NotNull(message = "A data não pode estar em branco")
    private LocalDateTime createdAt;

    /**
     * Buyer Id
     */
    @Positive(message = "O ID do comprador não deve ser nulo ou negativo")
    private Long buyerId;

    /**
     * Buyer Id
     */
    @Positive(message = "O ID do produto não deve ser nulo ou negativo")
    private Long productId;

    public CommentModel toModel() {
        BuyerModel buyer = new BuyerModel();
        buyer.setId(buyerId);

        ProductModel product = new ProductModel();
        product.setId(productId);
        return CommentModel.builder()
                .comment(comment)
                .createdAt(createdAt)
                .buyer(buyer)
                .product(product)
                .build();
    }

}
