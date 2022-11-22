package com.meli.frescos.controller.dto;

import com.meli.frescos.model.UserProfileEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request DTO for Message endpoints
 */
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {

    /**
     * Message sender_id
     */
    @NotNull(message = "O id do Sender não pode estar branco")
    @Positive(message = "O id do Sender deve ser um valor positivo")
    private Long sender_id;

    /**
     * Message senderProfileEnum
     */
    @NotNull(message = "O tipo de perfil não pode estar em branco")
    private UserProfileEnum senderProfileEnum;

    /**
     * Message receiver_id
     */
    @NotNull(message = "O id do Receiver não pode estar branco")
    @Positive(message = "O id do Receiver deve ser um valor positivo")
    private Long receiver_id;

    /**
     * Message receiverProfileEnum
     */
    @NotNull(message = "O tipo de perfil não pode estar em branco")
    private UserProfileEnum receiverProfileEnum;

    /**
     * Message message
     */
    @NotBlank(message = "A mensagem não pode estar em branco")
    @Size(min = 10, message = "A mensagem deve ter no mínimo 10 caracteres")
    private String message;

    /**
     * Message messageReaded
     */
    private boolean messageReaded;
}
