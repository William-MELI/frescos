package com.meli.frescos.service;

import com.meli.frescos.controller.dto.PurchaseOrderRequest;
import com.meli.frescos.exception.PurchaseOrderByIdNotFoundException;
import com.meli.frescos.model.PurchaseOrderModel;
import java.math.BigDecimal;
import java.util.List;

public interface IPurchaseOrderService {
    PurchaseOrderModel save(PurchaseOrderRequest purchaseOrderRequest);

    BigDecimal savePurchaseGetPrice(PurchaseOrderRequest purchaseOrderRequest);

    PurchaseOrderModel getById(Long purchaseId) throws PurchaseOrderByIdNotFoundException;

    List<PurchaseOrderModel> getAll();

   void updateStatus(Long id) throws Exception;
}
