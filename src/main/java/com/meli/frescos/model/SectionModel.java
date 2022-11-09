package com.meli.frescos.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "section")
public class SectionModel {

    public SectionModel(String description,
                        CategoryEnum category,
                        Double totalSize,
                        Double temperature,
                        WarehouseModel warehouse) {
        this.description = description;
        this.category = category;
        this.totalSize = totalSize;
        this.temperature = temperature;
        this.warehouse = warehouse;
    }

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
