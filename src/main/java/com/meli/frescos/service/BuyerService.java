package com.meli.frescos.service;

import com.meli.frescos.exception.SellerByIdNotFoundException;
import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.model.SellerModel;
import com.meli.frescos.repository.BuyerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuyerService implements IBuyerService {

    private final BuyerRepository repo;

    public BuyerService(BuyerRepository repo) {
        this.repo = repo;
    }

    @Override
    public BuyerModel save(BuyerModel buyerModel) {

        return repo.save(buyerModel);
    }

    @Override
    public List<BuyerModel> findAll() {
        return repo.findAll();
    }

    @Override
    public BuyerModel findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new SellerByIdNotFoundException(id));
    }

    @Override
    public BuyerModel update(BuyerModel buyerModel, Long id) {
        BuyerModel seller = findById(id);
        buyerModel.setId(seller.getId());
        return repo.save(buyerModel);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Optional<BuyerModel> findByCpf(String cpf) {
        return repo.findByCpf(cpf);
    }
}
