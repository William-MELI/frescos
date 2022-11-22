package com.meli.frescos.controller.dto;

import lombok.*;
import java.time.LocalDateTime;

/**
 * Response DTO for Message endpoints
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyMessagesResponse {

    /**
     * Message id
     */
    private Long message_id;

    /**
     * Message sender_name
     */
    private String sender_name;

    /**
     * Message content
     */
    private String message;

    /**
     * Message messageReaded
     */
    private boolean messageReaded;

    /**
     * Message received_at
     */
    private LocalDateTime received_at;
}
