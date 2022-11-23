package com.meli.frescos.controller.dto;

import com.meli.frescos.model.CommentModel;
import lombok.*;

/**
 * DTO response to endpoints related to Seller
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentPatchResponse {

    /**
     * Comment text
     */
    private String comment;
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
    public static CommentPatchResponse toResponse(CommentModel commentModel) {

        return CommentPatchResponse.builder()
                .id(commentModel.getId())
                .comment(commentModel.getComment())
                .build();
    }

}
