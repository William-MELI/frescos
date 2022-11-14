package com.meli.frescos.repository;

import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.model.SectionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BatchStockRepository extends JpaRepository<BatchStockModel, Long> {
    List<BatchStockModel> findByProduct(ProductModel product);
    List<BatchStockModel> findByProductAndDueDateGreaterThanEqual(ProductModel product, LocalDate dueDate);
    List<BatchStockModel> findBySection(SectionModel section);

    List<BatchStockModel> findBySectionAndDueDateBetween(SectionModel section, LocalDate dueDate, LocalDate dueDate2);
    @Query("FROM BatchStockModel bsm where bsm.product.id = :productId and bsm.dueDate >= :dateToCompare")
    List<BatchStockModel> findProducts(@Param("productId") Long productModel, @Param("dateToCompare") LocalDate dateToCompare);
}
