package com.meli.frescos.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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
    @Column(nullable=false)
    private Long id;

    /**
     * Representative name
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
