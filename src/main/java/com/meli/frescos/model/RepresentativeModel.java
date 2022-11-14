package com.meli.frescos.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "representative")
public class RepresentativeModel {

    /**
     * Representative ID.
     * Auto-generated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Representativre name
     * Not nullable.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Warehouse reference.
     * It is an 1-1 relationship
     */
    @OneToOne(optional = false)
    private WarehouseModel warehouse;
}
