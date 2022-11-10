package com.meli.frescos.repository;

import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.model.SectionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchStockRepository extends JpaRepository<BatchStockModel, Long> {
    List<BatchStockModel> findByProduct(ProductModel product);

    List<BatchStockModel> findBySection(SectionModel section);
}
