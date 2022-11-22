package com.meli.frescos.model;

import lombok.*;

import javax.persistence.*;

/**
 * Main Buyer Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "buyer")
public class BuyerModel {
    /**
     * Buyer ID
     * Auto-generated. Identity Strategy
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Buyer name.
     * Not-nullable
     */
    @Column(nullable = false)
    private String name;
    /**
     * Buyer CPF
     * Unique
     * Only numbers
     * Not-nullable
     */
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(columnDefinition = "varchar(32) default 'Buyer'")
    private UserProfileEnum userProfileEnum = UserProfileEnum.Buyer;

    public BuyerModel(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
    }
}
