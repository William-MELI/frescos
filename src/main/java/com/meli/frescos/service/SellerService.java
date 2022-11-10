package com.meli.frescos.service;

import com.meli.frescos.exception.SellerByIdNotFoundException;
import com.meli.frescos.model.SellerModel;
import com.meli.frescos.repository.SellerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SellerService implements ISellerService{

    private final SellerRepository repo;

    public SellerService(SellerRepository repo) {
        this.repo = repo;
    }

    @Override
    public SellerModel save(SellerModel sellerModel) {
        return repo.save(sellerModel);
    }

    @Override
    public List<SellerModel> findAll() {
        return repo.findAll();
    }

    @Override
    public SellerModel findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new SellerByIdNotFoundException(id));
    }

    @Override
    public SellerModel update(SellerModel sellerModel, Long id) {
        SellerModel seller = findById(id);
        sellerModel.setId(seller.getId());
        return repo.save(sellerModel);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Optional<SellerModel> findByCpf(String cpf) {
        return repo.findByCpf(cpf);
    }
}
