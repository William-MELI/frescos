package com.meli.frescos.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Main Message Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message")
public class MessageModel {

    /**
     * Message ID.
     * Auto-generated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Message sender_id
     * Not nullable.
     */
    @Column(nullable = false)
    private Long sender_id;

    /**
     * Message senderProfile
     * Not nullable.
     */
    @Enumerated(EnumType.STRING)
    private UserProfileEnum senderProfileEnum;

    /**
     * Message receiver_id
     * Not nullable.
     */
    @Column(nullable = false)
    private Long receiver_id;

    /**
     * Message receiverProfile
     * Not nullable.
     */
    @Enumerated(EnumType.STRING)
    private UserProfileEnum receiverProfileEnum;

    /**
     * Message content
     * Not nullable.
     */
    @Column(nullable = false)
    private String message;

    /**
     * Message messageReaded
     * Not nullable.
     */
    @Column(nullable = false)
    private boolean messageReaded;

    /**
     * Message created_at
     * Not nullable.
     */
    @Column(nullable = false)
    private LocalDateTime created_at = LocalDateTime.now();
}
