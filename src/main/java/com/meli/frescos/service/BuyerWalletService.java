package com.meli.frescos.service;

import com.meli.frescos.exception.BuyerAlreadyHasWalletException;
import com.meli.frescos.exception.BuyerWalletNotExistException;
import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.model.BuyerWalletModel;
import com.meli.frescos.repository.BuyerWalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class related to buyer wallet functions
 * Using Spring @Service
 */
@Service
public class BuyerWalletService implements IBuyerWalletService {

    private final BuyerWalletRepository buyerWalletRepository;

    private final BuyerService buyerService;

    public BuyerWalletService(BuyerWalletRepository buyerWalletRepository, BuyerService buyerService) {
        this.buyerWalletRepository = buyerWalletRepository;
        this.buyerService = buyerService;
    }

    /**
     * This method save new wallet to a buyer
     * @param buyerWalletModel
     * @param buyerId
     * @return buyer wallet
     * @throws Exception
     */
    @Override
    public BuyerWalletModel save(BuyerWalletModel buyerWalletModel, Long buyerId) throws BuyerAlreadyHasWalletException {
        BuyerModel buyer = buyerService.getById(buyerId);
        List<BuyerWalletModel> findBuyer = getByBuyer(buyer);
        if (findBuyer.isEmpty()) {
            buyerWalletModel.setBuyer(buyer);
            BuyerWalletModel response = buyerWalletRepository.save(buyerWalletModel);
            return response;
        } else {
            throw new BuyerAlreadyHasWalletException(buyer.getId());
        }
    }

    /**
     * This method find a wallet giving buyerModel
     * @param buyerModel
     * @return List of buyer wallet
     */
    @Override
    public List<BuyerWalletModel> getByBuyer(BuyerModel buyerModel) {
        List<BuyerWalletModel> buyerWalletModel = buyerWalletRepository.findByBuyer(buyerModel);

        return buyerWalletModel;
    }

    /**
     * This method find buyer wallet giving id
     * @param id
     * @return buyer wallet model
     * @throws BuyerWalletNotExistException
     */
    @Override
    public BuyerWalletModel getById(Long id) throws BuyerWalletNotExistException {
        return buyerWalletRepository.findById(id).orElseThrow(() -> new BuyerWalletNotExistException(id));
    }

    /**
     * This method deposit value to buyer balance
     * @param id
     * @param value
     * @return new balance from buyer wallet
     */
    @Override
    public BuyerWalletModel deposit(Long id, Double value) {

        BuyerModel buyerModel = buyerService.getById(id);

        List<BuyerWalletModel> buyerWalletModel = getByBuyer(buyerModel);
        buyerWalletModel.get(0).setBalance(Double.sum(buyerWalletModel.get(0).getBalance(), value));
        return buyerWalletRepository.save(buyerWalletModel.get(0));
    }
}
