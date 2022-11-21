package com.meli.frescos.repository;

import com.meli.frescos.model.OrderProductsModel;
import com.meli.frescos.model.PurchaseOrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderProductsRepository extends JpaRepository<OrderProductsModel, Long> {
    public List<OrderProductsModel> findByPurchaseOrderModel_Id(Long id);
}
