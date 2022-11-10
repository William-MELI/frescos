package com.meli.frescos.repository;

import com.meli.frescos.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    @Query(value = "select * from product where category = ?1", nativeQuery = true)
    List<ProductModel> getProductByCategory(int filter);
}
