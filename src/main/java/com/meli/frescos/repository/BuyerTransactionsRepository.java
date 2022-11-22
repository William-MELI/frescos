package com.meli.frescos.repository;

import com.meli.frescos.model.BuyerTransactionsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerTransactionsRepository extends JpaRepository<BuyerTransactionsModel, Long> {
}
