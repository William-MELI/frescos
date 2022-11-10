package com.meli.frescos.repository;

import com.meli.frescos.model.OrderProductsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductsRepository extends JpaRepository<OrderProductsModel, Long> {
}
