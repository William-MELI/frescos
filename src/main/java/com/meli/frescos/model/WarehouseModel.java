package com.meli.frescos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Main Warehouse Entity
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "warehouse")
public class WarehouseModel {

    /**
    *   Warehouse ID.
     *   Auto-generated
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *   District name.
     *   Not nullable.
     */
    @Column(nullable = false)
    private String district;

    /**
     *   State name
     *   Not nullable.
     */
    @Column(nullable = false)
    private String state;

    /**
     *   City name
     *   Not nullable.
     */
    @Column(nullable = false)
    private String city;

    /**
     *   Street name
     *   Not nullable.
     */
    @Column(nullable = false)
    private String street;

    /**
     *   Postal Code value. Only numbers.
     *   Not nullable.
     */
    @Column(nullable = false)
    private String postalCode;

    public WarehouseModel(String city, String state, String street, String postalCode, String district){
        this.city = city;
        this.state = state;
        this.street = street;
        this.postalCode = postalCode;
        this.district = district;
    }
}
