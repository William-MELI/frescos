package com.meli.frescos.service;

import com.meli.frescos.model.BatchStockModel;

import java.util.List;

public interface IBatchStockService {

    List<BatchStockModel>findAll() throws Exception;
    BatchStockModel findById(Long id) throws Exception;
}
