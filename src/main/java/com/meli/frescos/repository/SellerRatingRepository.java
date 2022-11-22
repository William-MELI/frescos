package com.meli.frescos.repository;

import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.model.PurchaseOrderModel;
import com.meli.frescos.model.SellerModel;
import com.meli.frescos.model.SellerRatingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SellerRatingRepository extends JpaRepository<SellerRatingModel, Long> {
    List<SellerRatingModel> findBySeller(SellerModel seller);
    SellerRatingModel findBySellerAndBuyerAndPurchaseOrder(SellerModel seller, BuyerModel buyer, PurchaseOrderModel purchaseOrder);
}
