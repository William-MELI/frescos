package com.meli.frescos.repository;

import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    List<ProductModel> findByCategory(CategoryEnum filter);
}
