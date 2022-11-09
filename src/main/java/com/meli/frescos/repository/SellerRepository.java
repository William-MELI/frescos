package com.meli.frescos.repository;

import com.meli.frescos.model.SellerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<SellerModel, Long> {

    Optional<SellerModel> findByCpf(String cpf);
}
