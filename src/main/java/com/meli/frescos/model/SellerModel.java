package com.meli.frescos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * The main Seller entity
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    public SellerModel(String name, String cpf, Double rating) {
        this.name = name;
        this.cpf = cpf;
        this.rating = rating;
    }
}
