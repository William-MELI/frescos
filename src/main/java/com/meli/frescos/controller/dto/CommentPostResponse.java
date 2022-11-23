package com.meli.frescos.controller.dto;

import com.meli.frescos.model.CommentModel;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO response to endpoints related to Seller
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentPostResponse {
    /**
     * Product name
     */
    private String productName;

    /**
     * Comment text
     */
    private String comment;
    /**
     * Date & Time of comment creation
     */
    private LocalDateTime createdAt;
    /**
     * Comment ID
     */
    private Long id;

    /**
     * Maps SellerModel to SellerResponse
     *
     * @param commentModel
     * @return CommentResponse
     */
    public static CommentPostResponse toResponse(CommentModel commentModel) {

        return CommentPostResponse.builder()
                .id(commentModel.getId())
                .comment(commentModel.getComment())
                .createdAt(commentModel.getCreatedAt())
                .productName(commentModel.getProduct().getProductTitle())
                .build();
    }

}
