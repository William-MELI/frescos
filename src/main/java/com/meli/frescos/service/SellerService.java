package com.meli.frescos.service;

import com.meli.frescos.model.SellerModel;
import com.meli.frescos.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerService implements ISellerService{

    private final SellerRepository repo;

    @Override
    public SellerModel save(SellerModel sellerModel) {
        return repo.save(sellerModel);
    }

    @Override
    public List<SellerModel> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<SellerModel> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public SellerModel update(SellerModel sellerModel) {
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
