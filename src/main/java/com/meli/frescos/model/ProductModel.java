package com.meli.frescos.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productTitle;

    private String description;

    private BigDecimal price;

    private CategoryEnum category;

    private Double unitVolume;

    private Double unitWeight;

    private LocalDate createDate;

    @ManyToOne
    private SellerModel seller;
}
