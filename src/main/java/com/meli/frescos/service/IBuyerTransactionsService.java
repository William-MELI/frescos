package com.meli.frescos.service;

import com.meli.frescos.model.BuyerTransactionsModel;
import com.meli.frescos.model.BuyerWalletModel;

import java.util.List;

public interface IBuyerTransactionsService {
    BuyerWalletModel saveDeposit(BuyerTransactionsModel buyerTransactionsModel, Long buyerId);

    List<BuyerTransactionsModel> getTransactionsByBuyerId(Long id);
}
