package com.meli.frescos.repository;

import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    List<ProductModel> findByCategory(CategoryEnum filter);

    @Query("SELECT DISTINCT(pm.id) FROM ProductModel pm INNER JOIN BatchStockModel bsm ON bsm.product.id = pm.id WHERE pm.id = :productId")
    ProductModel findProductByBatchstock(@Param("productId") Long productId);
}
