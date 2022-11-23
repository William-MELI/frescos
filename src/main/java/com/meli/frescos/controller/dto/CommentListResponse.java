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
public class CommentListResponse {
    /**
     * Product name
     */
    private String productName;

    /**
     * Seller rating
     */
    private List<CommentDetails> comments;

    /**
     * Maps SellerModel to SellerResponse
     *
     * @param commentModelList
     * @return CommentResponse
     */
    public static CommentListResponse toResponse(List<CommentModel> commentModelList) {
        List<CommentDetails> comments = new ArrayList<>();

        for (CommentModel commentModel : commentModelList)
            comments.add(CommentDetails.toResponse(commentModel));

        return CommentListResponse.builder()
                .comments(comments)
                .productName(commentModelList.get(0).getProduct().getProductTitle())
                .build();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class CommentDetails {

        String comment;
        LocalDateTime createdAt;
        Long id;

        public static CommentDetails toResponse(CommentModel commentModel) {
            return CommentDetails
                    .builder()
                    .comment(commentModel.getComment())
                    .createdAt(commentModel.getCreatedAt())
                    .id(commentModel.getId())
                    .build();
        }
    }
}
