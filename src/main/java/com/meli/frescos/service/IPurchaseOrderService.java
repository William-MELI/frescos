package com.meli.frescos.service;

import com.meli.frescos.controller.dto.OrderProductsRequest;
import com.meli.frescos.controller.dto.PurchaseOrderRequest;
import com.meli.frescos.exception.OrderProductIsInvalidException;
import com.meli.frescos.model.OrderStatusEnum;
import com.meli.frescos.model.PurchaseOrderModel;

import java.math.BigDecimal;
import java.util.List;

public interface IPurchaseOrderService {
    PurchaseOrderModel save(PurchaseOrderRequest purchaseOrderRequest);

    BigDecimal savePurchaseGetPrice(PurchaseOrderRequest purchaseOrderRequest);

    List<PurchaseOrderModel> getAll();

   void updateStatus(Long id)  throws Exception;
}
