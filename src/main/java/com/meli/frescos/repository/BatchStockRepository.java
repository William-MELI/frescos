package com.meli.frescos.repository;

import com.meli.frescos.model.BatchStockModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchStockRepository extends JpaRepository<BatchStockModel, Long> {
}
