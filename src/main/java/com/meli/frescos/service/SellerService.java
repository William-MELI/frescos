package com.meli.frescos.service;

import com.meli.frescos.exception.SellerByIdNotFoundException;
import com.meli.frescos.model.SellerModel;
import com.meli.frescos.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 *  This class contains all Seller related functions
 *  Using @Service from spring
 */
@Service
@RequiredArgsConstructor
public class SellerService implements ISellerService{

    private final SellerRepository repo;

    /**
     * Create a new Seller given model
     * @param sellerModel New Seller to create
     * @return The created Seller
     */
    @Override
    public SellerModel save(SellerModel sellerModel) {
        return repo.save(sellerModel);
    }

    /**
     * Returns all stored Seller
     * @return List of all Seller
     */
    @Override
    public List<SellerModel> findAll() {
        return repo.findAll();
    }

    /**
     * Returns a stored Seller given ID
     * @param id
     * @return The stored Seller
     * @throws SellerByIdNotFoundException Throws in case Seller does not exists
     */
    @Override
    public SellerModel findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new SellerByIdNotFoundException(id));
    }

    /**
     * Updates a stored Seller
     * @param sellerModel Used as reference to update stored Seller. Must contains ID with existent Seller
     * @throws SellerByIdNotFoundException Throws in case Seller does not exists
     */
    @Override
    public SellerModel update(SellerModel sellerModel, Long id) {
        SellerModel seller = findById(id);
        sellerModel.setId(seller.getId());
        return repo.save(sellerModel);
    }

    /**
     * Deletes a Seller given ID
     * @param id Existent Seller ID
     */
    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    /**
     * Returns a stored Seller given cpf
     * @param cpf
     * @return The stored Seller
     */
    @Override
    public Optional<SellerModel> findByCpf(String cpf) {
        return repo.findByCpf(cpf);
    }
}
