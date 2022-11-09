package com.meli.frescos.service;

import com.meli.frescos.exception.BatchStockByIdNotFoundException;
import com.meli.frescos.model.BatchStockModel;

import java.util.List;

public interface IBatchStockService {

    List<BatchStockModel> findAll();

    BatchStockModel findById(Long id) throws BatchStockByIdNotFoundException;
}
