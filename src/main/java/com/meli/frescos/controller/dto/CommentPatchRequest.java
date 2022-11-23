package com.meli.frescos.controller.dto;

import com.meli.frescos.model.CommentModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * Request DTO for Section endpoints
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentPatchRequest {
    /**
     * Commentary ID
     */
    @Positive(message = "ID do comentário deve ser positivo.")
    @NotNull(message= "ID do comentário não pode ser nulo.")
    private Long id;

    /**
     * Commentary text
     */
    @NotBlank(message = "O comentário não pode estar em branco")
    @Size(max = 255, message = "O comentário deve ter no máximo 255 caracteres")
    private String comment;


    public CommentModel toModel() {
        return CommentModel.builder()
                .comment(comment)
                .id(id)
                .build();
    }

}
