package com.meli.frescos.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "comment",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"product_id", "buyer_id"})
})
public class CommentModel {

    /**
     * Buyer ID
     * Auto-generated. Identity Strategy
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "product_id")
    ProductModel product;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    BuyerModel buyer;
}
