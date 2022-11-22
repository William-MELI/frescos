package com.meli.frescos.service;

import com.meli.frescos.model.ProductModel;
import java.util.List;

public interface IProductService {

    List<ProductModel> getAll();

    ProductModel getById(Long id);

    ProductModel save(ProductModel product);

    List<ProductModel> getByCategory(String filter);

    List<ProductModel> getByDescriptionContaining(String description);
}
