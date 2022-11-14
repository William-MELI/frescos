package com.meli.frescos.service;

import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.ProductModel;
import java.util.List;

public interface IProductService {

    List<ProductModel> getAll();

    ProductModel getById(Long id) throws Exception;

    ProductModel save(ProductModel product);

    List<ProductModel> getByCategory(String filter);

    ProductModel save(ProductModel product, List<BatchStockModel> batchStockList) throws Exception;
}
