package com.meli.frescos.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Main BatchStock Entity
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "batch_stock")
public class BatchStockModel {

    /**
     *  BatchStock ID
     *  Auto-generated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *  BatchStock number
     *  Not Nullable
     */
    @Column(nullable = false)
    private String batchNumber;

    /**
     *  BatchStock quantity
     *  Not Nullable
     */
    @Column(nullable = false)
    private Double quantity;

    /**
     *  BatchStock manufacture date
     *  Not Nullable
     */
    @Column(nullable = false)
    private LocalDate manufacturingDate;

    /**
     *  BatchStock manufacture date and time
     *  Not Nullable
     */
    @Column(nullable = false)
    private LocalDateTime manufacturingTime;

    /**
     *  BatchStock due date
     *  Not Nullable
     */
    @Column(nullable = false)
    private LocalDate dueDate;

    /**
     * Product related to BatchStock(Foreign key)
     * Not Nullable
     */
    @Column(nullable = false)
    @ManyToOne
    private ProductModel product;

    /**
     * Section related to BatchStock(Foreign key)
     * Not Nullable
     */
    @Column(nullable = false)
    @ManyToOne
    private SectionModel section;
}
