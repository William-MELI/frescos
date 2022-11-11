package com.meli.frescos.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Main Product Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductModel {

    /**
     * Product ID
     * Auto-generated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Product productTitle
     * Not Nullable
     */
    @Column(nullable = false)
    private String productTitle;

    /**
     * Product description
     * Not Nullable
     */
    @Column(nullable = false)
    private String description;

    /**
     * Product price
     * Not Nullable
     */
    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    /**
     * Product unitVolume
     * Not Nullable
     */
    @Column(nullable = false)
    private Double unitVolume;

    /**
     * Product unitWeight
     * Not Nullable
     */
    @Column(nullable = false)
    private Double unitWeight;

    /**
     * Product createDate
     * Not Nullable
     */
    @Column(nullable = false)
    private LocalDate createDate;

    /**
     * Seller reference.
     * It is an N-1 relationship
     */
    @ManyToOne
    private SellerModel seller;
}
