package com.meli.frescos.service;

import com.meli.frescos.model.BuyerWalletModel;

public interface IBuyerWalletService {

    BuyerWalletModel save(BuyerWalletModel buyerWalletModel, Long buyerId);
}
