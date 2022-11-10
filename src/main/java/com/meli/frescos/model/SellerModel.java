package com.meli.frescos.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seller")
public class SellerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String cpf;

    private Double rating;

    public SellerModel(String name, String cpf, Double rating) {
        this.name = name;
        this.cpf = cpf;
        this.rating = rating;
    }
}
