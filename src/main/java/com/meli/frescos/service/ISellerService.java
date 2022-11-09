package com.meli.frescos.service;

import com.meli.frescos.controller.dto.SellerResponse;
import com.meli.frescos.model.SellerModel;

import java.util.List;
import java.util.Optional;

public interface ISellerService {

    SellerModel save(SellerModel sellerModel);
    List<SellerModel> findAll();
    Optional<SellerModel> findById(Long id);
    SellerModel update(SellerModel sellerModel);
    void deleteById(Long id);

    Optional<SellerModel> findByCpf(String cpf);

}
