package com.meli.frescos.service;

import com.meli.frescos.exception.BuyerAlreadyHasWalletException;
import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.model.BuyerTransactionsModel;
import com.meli.frescos.model.BuyerWalletModel;
import com.meli.frescos.repository.BuyerTransactionsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class related to buyer transaction functions
 * Using Spring @Service
 */
@Service
public class BuyerTransactionsService implements IBuyerTransactionsService{

    private final BuyerTransactionsRepository buyerTransactionsRepository;

    private final BuyerService buyerService;

    private final BuyerWalletService buyerWalletService;

    public BuyerTransactionsService(BuyerTransactionsRepository buyerTransactionsRepository, BuyerService buyerService, BuyerWalletService buyerWalletService) {
        this.buyerTransactionsRepository = buyerTransactionsRepository;
        this.buyerService = buyerService;
        this.buyerWalletService = buyerWalletService;
    }

    /**
     * This method save deposit in buyer wallet
     * @param buyerTransactionsModel
     * @param buyerId
     * @return buyer wallet with new balance
     * @throws BuyerAlreadyHasWalletException
     */
    @Override
    public BuyerWalletModel saveDeposit(BuyerTransactionsModel buyerTransactionsModel, Long buyerId) throws BuyerAlreadyHasWalletException {

        BuyerModel buyerModel = buyerService.getById(buyerId);

        List<BuyerWalletModel> buyerWalletModel = buyerWalletService.getByBuyer(buyerModel);

        if(buyerWalletModel.isEmpty()) {
            throw new BuyerAlreadyHasWalletException(buyerModel.getId());
        } else {
            buyerTransactionsModel.setDate(LocalDateTime.now());
            buyerTransactionsModel.setBuyerWallet(buyerWalletModel.get(0));
            buyerTransactionsModel.setTypeOfTransaction("Deposito");
            buyerTransactionsRepository.save(buyerTransactionsModel);
            return buyerWalletService.deposit(buyerId, buyerTransactionsModel.getValue());
        }
    }

    /**
     * This method get transactions from buyer id
     * @param id
     * @return all transactions from buyer id
     */
    @Override
    public List<BuyerTransactionsModel> getTransactionsByBuyerId(Long id) {
        BuyerModel buyerModel = buyerService.getById(id);

        List<BuyerWalletModel> buyerWalletModel = buyerWalletService.getByBuyer(buyerModel);

        List<BuyerTransactionsModel> buyerTransactionsModels = buyerTransactionsRepository.findByBuyerWallet(buyerWalletModel.get(0));

        return buyerTransactionsModels;
    }
}
