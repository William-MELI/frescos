package com.meli.frescos.service;

import com.meli.frescos.exception.BuyerWalletNotExistException;
import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.model.BuyerWalletModel;

import java.util.List;

public interface IBuyerWalletService {

    BuyerWalletModel save(BuyerWalletModel buyerWalletModel, Long buyerId) throws Exception;

    List<BuyerWalletModel> getByBuyer(BuyerModel buyerModel);

    BuyerWalletModel getById(Long id) throws BuyerWalletNotExistException;

    BuyerWalletModel deposit(Long id, Double value);
}
