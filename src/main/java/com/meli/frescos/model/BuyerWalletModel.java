package com.meli.frescos.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "buyer_wallet")
public class BuyerWalletModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double balance;

    @OneToOne
    @JoinColumn(name = "buyer")
    private BuyerModel buyer;

}
