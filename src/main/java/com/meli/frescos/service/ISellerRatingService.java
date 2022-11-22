package com.meli.frescos.service;

import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.model.PurchaseOrderModel;
import com.meli.frescos.model.SellerModel;
import com.meli.frescos.model.SellerRatingModel;
import java.util.List;

public interface ISellerRatingService {

    SellerRatingModel save(SellerRatingModel sellerRating);
    SellerRatingModel getBySellerAndBuyerAndPurchaseOrder(SellerModel seller, BuyerModel buyer, PurchaseOrderModel purchaseOrder);
    List<SellerRatingModel> getAll();
}
