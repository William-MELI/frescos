package com.meli.frescos.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Main Refrigerator Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Refrigerator {

    /**
     * Refrigerator id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Refrigerator brand
     */
    @Column(nullable = false)
    private String brand;

    /**
     * Refrigerator model
     */
    @Column(nullable = false)
    private String model;

    /**
     * Refrigerator desired set temperature
     */
    @Column(nullable = false)
    private Double temperature;

    /**
     * Refrigerator active status
     */
    @Column(nullable = false)
    private Boolean turnedOn;

    /**
     * Refrigerator acquirement date
     */
    @Column(nullable = false)
    private LocalDateTime acquired;


    /**
     * Refrigerator last revision date and time
     */
    private LocalDateTime lastRevision;

    /**
     * Refrigerator section
     */
    @OneToOne
    private SectionModel section;

}
