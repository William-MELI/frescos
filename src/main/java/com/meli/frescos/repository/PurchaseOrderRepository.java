package com.meli.frescos.repository;

import com.meli.frescos.model.PurchaseOrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrderModel, Long> {
}
