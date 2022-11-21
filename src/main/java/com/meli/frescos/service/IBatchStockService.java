package com.meli.frescos.service;

import com.meli.frescos.exception.*;
import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.model.PurchaseOrderModel;

import java.time.LocalDate;
import java.util.List;

public interface IBatchStockService {

    List<BatchStockModel> getAll();

    BatchStockModel getById(Long id) throws BatchStockByIdNotFoundException;

    BatchStockModel save(BatchStockModel batchStock) ;

    List<BatchStockModel> getByProductId(Long productId);

    List<BatchStockModel> getBySectionId(Long sectionId) ;

    List<BatchStockModel> getBySectionIdAndDueDate(Long sectionId, Integer numberOfDays) ;

    List<BatchStockModel> getByCategoryAndDueDate(CategoryEnum category, Integer numberOfDays) ;

    Integer getTotalBatchStockQuantity(Long productId) ;

    LocalDate getClosestDueDate(Long productId) throws NullDueDateException;

    void validateBatches(ProductModel product, List<BatchStockModel> batchStockList) throws ProductNotPermittedInSectionException, NotEnoughSpaceInSectionException;

    List<BatchStockModel> findValidProductsByDueDate(Long productModel, LocalDate minDueDate);

    List<BatchStockModel> getByProductOrder(Long id, String order);

    void consumeBatchStockOnPurchase(PurchaseOrderModel purchaseOrderModel) throws NotEnoughStockException;
}