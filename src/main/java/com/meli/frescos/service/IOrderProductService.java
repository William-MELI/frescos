package com.meli.frescos.service;

import com.meli.frescos.controller.dto.OrderProductsRequest;
import com.meli.frescos.exception.OrderProductIsInvalidException;
import com.meli.frescos.exception.ProductByIdNotFoundException;
import com.meli.frescos.exception.PurchaseOrderByIdNotFoundException;
import com.meli.frescos.model.OrderProductsModel;

import java.util.List;

public interface IOrderProductService {
    List<OrderProductsModel> getAll();

    OrderProductsModel save(OrderProductsRequest orderProductsRequest) throws ProductByIdNotFoundException, PurchaseOrderByIdNotFoundException;

    OrderProductsModel getById(Long id) throws OrderProductIsInvalidException;

    List<OrderProductsModel> getByPurchaseId(Long id) throws PurchaseOrderByIdNotFoundException;
}
