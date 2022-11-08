package com.meli.frescos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "section")
public class SectionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    private Double totalSize;

    private Double temperature;

    @ManyToOne
    private WarehouseModel warehouse;
}
