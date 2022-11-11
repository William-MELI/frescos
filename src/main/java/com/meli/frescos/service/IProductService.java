package com.meli.frescos.service;

import com.meli.frescos.model.ProductModel;
import java.util.List;

public interface IProductService {

    List<ProductModel> getAll();

    ProductModel getById(Long id) throws Exception;

    ProductModel save(ProductModel product, Long sellerCode);

    List<ProductModel> getByCategory(String filter);
}
