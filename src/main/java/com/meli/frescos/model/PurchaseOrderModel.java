package com.meli.frescos.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Main PurchaseOrder Entity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "purchase_order")
public class PurchaseOrderModel {

    /**
     * PurchaseOrder ID.
     * Auto-generated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * PurchaseOrder date
     * Not nullable.
     */
    @Column(nullable = false)
    private LocalDate date;

    /**
     * PurchaseOrder orderStatus
     * Not nullable.
     */
    @Column(nullable = false)
    private String orderStatus;

    /**
     * BuyerModel reference.
     * It is an N-1 relationship
     */
    @JoinColumn(nullable = false)
    @ManyToOne
    private BuyerModel buyer;

}
