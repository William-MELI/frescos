package com.meli.frescos.service;

import com.meli.frescos.exception.SellerRatingAlreadyExist;
import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.model.PurchaseOrderModel;
import com.meli.frescos.model.SellerModel;
import com.meli.frescos.model.SellerRatingModel;
import com.meli.frescos.repository.SellerRatingRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * This class contains all SellerRating related functions
 * Using @Service from spring
 */
@Service
public class SellerRatingService implements ISellerRatingService{

    private final SellerRatingRepository sellerRatingRepository;
    private final ISellerService iSellerService;

    public SellerRatingService(SellerRatingRepository sellerRatingRepository, ISellerService iSellerService) {
        this.sellerRatingRepository = sellerRatingRepository;
        this.iSellerService = iSellerService;
    }

    /**
     * Create a new SellerRating given model
     *
     * @param sellerRating New SellerRating to create
     * @return The created SellerRating
     */
    @Override
    public SellerRatingModel save(SellerRatingModel sellerRating) {
        SellerRatingModel sellerRatingVerify = getBySellerAndBuyerAndPurchaseOrder(sellerRating.getSeller(), sellerRating.getBuyer(), sellerRating.getPurchaseOrder());
        if(sellerRatingVerify != null)
            throw new SellerRatingAlreadyExist("Avaliação com mesma ordem de compra, vendedor e comprador já registrada");

        SellerRatingModel sellerRatingModel = sellerRatingRepository.save(sellerRating);
        updateSellerRating(sellerRating);
        return sellerRatingModel;
    }

    /**
     * Returns SellerRating by seller, buyer and purchase order
     *
     * @param seller the SellerModel
     * @param buyer the BuyerModel
     * @param purchaseOrder the PurchaseOrderModel
     * @return a SellerRatingModel
     */
    @Override
    public SellerRatingModel getBySellerAndBuyerAndPurchaseOrder(SellerModel seller, BuyerModel buyer, PurchaseOrderModel purchaseOrder) {
        return sellerRatingRepository.findBySellerAndBuyerAndPurchaseOrder(seller, buyer, purchaseOrder);
    }

    /**
     * Returns all stored SellerRating
     *
     * @return List of all SellerRating
     */
    @Override
    public List<SellerRatingModel> getAll() {
        return sellerRatingRepository.findAll();
    }

    /**
     * Update seller rating
     *
     * @param seller the SellerRating
     */
    private void updateSellerRating(SellerRatingModel seller){

        SellerModel sellerModel = iSellerService.getById(seller.getSeller().getId());

        List<SellerRatingModel> ratingSeller = sellerRatingRepository.findBySeller(seller.getSeller());

        Double avgSeller = ratingSeller.stream().mapToDouble(SellerRatingModel::getRating).average().getAsDouble();
        sellerModel.setRating(avgSeller);

        iSellerService.update(sellerModel, sellerModel.getId());

    }
}
