package com.meli.frescos.controller.dto;

import com.meli.frescos.model.CommentModel;
import lombok.*;

import java.time.LocalDate;
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
public class CommentResponse {

    /**
     * Product name
     */
    private String productName;

    /**
     * Seller rating
     */
    private List<CommentSimplified> comments;

    /**
     * Maps SellerModel to SellerResponse
     *
     * @param commentModelList
     * @return CommentResponse
     */
    public static CommentResponse toResponse(List<CommentModel> commentModelList) {
        List<CommentSimplified> comments = new ArrayList<>();

        for (CommentModel commentModel : commentModelList)
            comments.add(CommentSimplified.toResponse(commentModel));

        return CommentResponse.builder()
                .comments(comments)
                .productName(commentModelList.get(0).getProduct().getProductTitle())
                .build();
    }

    /**
     * Maps SellerModel to SellerResponse
     *
     * @param commentModel
     * @return CommentResponse
     */
    public static CommentResponse toResponse(CommentModel commentModel) {
        List<CommentSimplified> comments = new ArrayList<>();
        comments.add(CommentSimplified.toResponse(commentModel));
        return CommentResponse.builder()
                .comments(comments)
                .productName(commentModel.getProduct().getProductTitle())
                .build();
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class CommentSimplified {

        String comment;
        LocalDate createdAt;

        public static CommentSimplified toResponse(CommentModel commentModel) {
            return CommentSimplified.builder()
                    .comment(commentModel.getComment())
                    .createdAt(commentModel.getCreatedAt())
                    .build();
        }
    }
}
