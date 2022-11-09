package com.meli.frescos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "batch_stock")
public class BatchStockModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String batchNumber;

    private Double quantity;

    private LocalDate manufacturingDate;

    private LocalDateTime manufacturingTime;

    private LocalDate dueDate;

    @ManyToOne
    private ProductModel product;

    @ManyToOne
    private SectionModel section;
}
