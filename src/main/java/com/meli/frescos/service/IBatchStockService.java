package com.meli.frescos.service;

import com.meli.frescos.exception.BatchStockByIdNotFoundException;
import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.ProductModel;

import java.util.List;

public interface IBatchStockService {

    List<BatchStockModel> getAll();

    BatchStockModel getById(Long id) throws BatchStockByIdNotFoundException;

    BatchStockModel save(BatchStockModel batchStock, Long productId, Long sectionId, Long representativeId, Long warehouseId) throws Exception;

    List<BatchStockModel> findByProductId(Long productId);

    List<BatchStockModel> findBySectionId(Long sectionId) throws Exception;

    Integer getTotalBatchStockQuantity(Long productId) throws Exception;

    boolean isValid(ProductModel product, List<BatchStockModel> batchStockList, Long sectionId) throws Exception;

    List<BatchStockModel> findByProductOrder(Long id, String order);
}
