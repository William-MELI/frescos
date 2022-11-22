package com.meli.frescos.repository;

import com.meli.frescos.model.BuyerTransactionsModel;
import com.meli.frescos.model.BuyerWalletModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuyerTransactionsRepository extends JpaRepository<BuyerTransactionsModel, Long> {
    List<BuyerTransactionsModel> findByBuyerWallet(BuyerWalletModel buyerWalletModel);
}
