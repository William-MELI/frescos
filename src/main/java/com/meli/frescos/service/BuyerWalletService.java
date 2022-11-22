package com.meli.frescos.service;

import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.model.BuyerWalletModel;
import com.meli.frescos.repository.BuyerWalletRepository;
import org.springframework.stereotype.Service;

@Service
public class BuyerWalletService implements IBuyerWalletService {

    private final BuyerWalletRepository buyerWalletRepository;

    private final BuyerService buyerService;

    public BuyerWalletService (BuyerWalletRepository buyerWalletRepository, BuyerService buyerService) {
        this.buyerWalletRepository = buyerWalletRepository;
        this.buyerService =buyerService;
    }


    @Override
    public BuyerWalletModel save(BuyerWalletModel buyerWalletModel, Long buyerId) {
        BuyerModel buyer = buyerService.getById(buyerId);
        buyerWalletModel.setBuyer(buyer);
        BuyerWalletModel response = buyerWalletRepository.save(buyerWalletModel);

        return response;
    }
}
