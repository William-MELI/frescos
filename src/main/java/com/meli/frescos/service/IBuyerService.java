package com.meli.frescos.service;

import com.meli.frescos.exception.BuyerNotFoundException;
import com.meli.frescos.model.BuyerModel;

import java.util.List;
import java.util.Optional;

public interface IBuyerService {

    BuyerModel save(BuyerModel buyerModel);

    List<BuyerModel> findAll();

    BuyerModel findById(Long id) throws BuyerNotFoundException;

    BuyerModel update(BuyerModel buyerModel, Long id) throws BuyerNotFoundException;

    void deleteById(Long id);

    Optional<BuyerModel> findByCpf(String cpf);

}
