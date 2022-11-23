package com.meli.frescos.model;

import lombok.*;

import javax.persistence.*;

/**
 * The main Seller entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seller")
public class SellerModel {

    /**
     * SellerModel ID
     * Auto-generated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * SellerModel name
     * Not nullable
     */
    @Column(nullable = false)
    private String name;

    /**
     * SellerModel cpf
     * Unique
     * Not nullable
     */
    @Column(nullable = false, unique = true)
    private String cpf;

    /**
     * SellerModel rating
     */
    private Double rating;

    /**
     * SellerModel userProfile
     * Not nullable
     */
    @Column(nullable = false)
    private UserProfileEnum userProfileEnum;

    public SellerModel(String name, String cpf, Double rating, UserProfileEnum userProfileEnum) {
        this.name = name;
        this.cpf = cpf;
        this.rating = rating;
        this.userProfileEnum = userProfileEnum;
    }
}
