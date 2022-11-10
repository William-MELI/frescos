package com.meli.frescos.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Main BatchStock Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "batch_stock")
public class BatchStockModel {

    /**
     *  BatchStockModel ID
     *  Auto-generated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *  BatchStockModel number
     *  Not Nullable
     */
    @Column(nullable = false)
    private String batchNumber;

    /**
     *  BatchStockModel quantity
     *  Not Nullable
     */
    @Column(nullable = false)
    private Integer quantity;

    /**
     *  BatchStockModel manufacture date
     *  Not Nullable
     */
    @Column(nullable = false)
    private LocalDate manufacturingDate;

    /**
     *  BatchStockModel manufacture date and time
     *  Not Nullable
     */
    @Column(nullable = false)
    private LocalDateTime manufacturingTime;

    /**
     *  BatchStockModel due date
     *  Not Nullable
     */
    @Column(nullable = false)
    private LocalDate dueDate;

    /**
     * Product related to BatchStockModel(Foreign key)
     * Not Nullable
     */
    @JoinColumn(nullable = false)
    @ManyToOne
    private ProductModel product;

    /**
     * Section related to BatchStockModel(Foreign key)
     * Not Nullable
     */
    @JoinColumn(nullable = false)
    @ManyToOne
    private SectionModel section;
}
