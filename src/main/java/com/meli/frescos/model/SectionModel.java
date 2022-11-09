package com.meli.frescos.model;

import lombok.*;

import javax.persistence.*;

/**
 * Main Section Entity
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
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

    /**
     *   Section ID.
     *   Auto-generated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     *   Section description.
     *   Not nullable.
     */
    @Column(nullable = false)
    private String description;

    /**
     *   Section category.
     *   Not nullable.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    /**
     *   Section totalSize.
     *   Not nullable.
     */
    @Column(nullable = false)
    private Double totalSize;

    /**
     *   Section temperature.
     *   Not nullable.
     */
    @Column(nullable = false)
    private Double temperature;

    /**
     * Warehouse reference.
     * It is an N-1 relationship
     */
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private WarehouseModel warehouse;
}
