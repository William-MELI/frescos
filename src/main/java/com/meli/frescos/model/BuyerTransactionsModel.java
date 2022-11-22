package com.meli.frescos.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "buyer_transaction")
public class BuyerTransactionsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    private String typeOfTransaction;

    @JoinColumn(name = "buyer_wallet")
    @ManyToOne
    private BuyerWalletModel buyerWallet;
}
