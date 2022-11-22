package com.meli.frescos.repository;

import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.model.BuyerWalletModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuyerWalletRepository extends JpaRepository<BuyerWalletModel, Long> {
    List<BuyerWalletModel> findByBuyer(BuyerModel buyerModel);
}
