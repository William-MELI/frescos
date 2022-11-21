package com.meli.frescos.repository;

import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.model.SellerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuyerRepository extends JpaRepository<BuyerModel, Long> {

    Optional<BuyerModel> findByCpf(String cpf);
}
