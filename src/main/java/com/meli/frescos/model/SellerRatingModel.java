package com.meli.frescos.model;

import lombok.*;

import javax.persistence.*;

/**
 * Main SellerRating Entity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "seller_rating")
public class SellerRatingModel {

    /**
     * SellerRating ID.
     * Auto-generated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * SellerRating seller
     */
    @ManyToOne
    private SellerModel seller;

    /**
     * SellerRating purchase order
     */
    @ManyToOne
    private PurchaseOrderModel purchaseOrder;

    /**
     * SellerRating buyer
     */
    @ManyToOne
    private BuyerModel buyer;

    /**
     * SellerRating rating
     */
    @Column(nullable = false)
    private Double rating;
}
