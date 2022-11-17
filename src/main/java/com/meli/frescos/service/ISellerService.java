package com.meli.frescos.service;

import com.meli.frescos.model.SellerModel;

import java.util.List;
import java.util.Optional;

public interface ISellerService {

    SellerModel save(SellerModel sellerModel);

    List<SellerModel> getAll();

    SellerModel getById(Long id);

    SellerModel update(SellerModel sellerModel, Long id);

    void deleteById(Long id);

    Optional<SellerModel> getByCpf(String cpf);
}
