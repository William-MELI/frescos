package com.meli.frescos.service;

import com.meli.frescos.exception.BatchStockByIdNotFoundException;
import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.model.ProductModel;

import java.time.LocalDate;
import java.util.List;

public interface IBatchStockService {

    List<BatchStockModel> getAll();

    BatchStockModel getById(Long id) throws BatchStockByIdNotFoundException;

    BatchStockModel save(BatchStockModel batchStock) throws Exception;

    List<BatchStockModel> getByProductId(Long productId) throws Exception;

    List<BatchStockModel> getBySectionId(Long sectionId) throws Exception;

    List<BatchStockModel> getBySectionIdAndDueDate(Long sectionId, Integer numberOfDays) throws Exception;

    List<BatchStockModel> getByCategoryAndDueDate(CategoryEnum category, Integer numberOfDays) throws Exception;

    Integer getTotalBatchStockQuantity(Long productId) throws Exception;

    LocalDate getClosestDueDate(Long productId) throws Exception;

    void validateBatches(ProductModel product, List<BatchStockModel> batchStockList) throws Exception;

    List<BatchStockModel> findValidProductsByDueDate(Long productModel, LocalDate minDueDate) throws Exception;
}
