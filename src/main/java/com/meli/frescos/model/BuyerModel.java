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

    /**
     * Buyer userProfile.
     * Not-nullable
     */
    @Column(nullable = false)
    private UserProfileEnum userProfileEnum;

    public BuyerModel(String name, String cpf, UserProfileEnum userProfileEnum) {
        this.name = name;
        this.cpf = cpf;
        this.userProfileEnum = userProfileEnum;
    }
}
