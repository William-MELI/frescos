package com.meli.frescos.service;

import com.meli.frescos.controller.dto.PurchaseOrderRequest;
import com.meli.frescos.model.PurchaseOrderModel;

import java.util.List;

public interface IPurchaseOrderService {
    PurchaseOrderModel save(PurchaseOrderRequest purchaseOrderRequest);

    List<PurchaseOrderModel> getAll();

    PurchaseOrderModel updateStatus(Long id, String orderStatus);
}
