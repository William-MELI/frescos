package com.meli.frescos.service;

import com.meli.frescos.controller.dto.OrderProductsRequest;
import com.meli.frescos.model.OrderProductsModel;

import java.util.List;

public interface IOrderProductService {
    List<OrderProductsModel> getAll();

    OrderProductsModel save(OrderProductsRequest orderProductsRequest);

    OrderProductsModel getById(Long id) throws Exception;
}
