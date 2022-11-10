package com.meli.frescos.service;

import com.meli.frescos.exception.CpfDuplicateException;
import com.meli.frescos.exception.SellerByIdNotFoundException;
import com.meli.frescos.model.SellerModel;
import com.meli.frescos.repository.SellerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 *  This class contains all Seller related functions
 *  Using @Service from spring
 */
@Service
public class SellerService implements ISellerService {

    private final SellerRepository repo;

    public SellerService(SellerRepository repo) {
        this.repo = repo;
    }

    /**
     * Create a new Seller given model
     * @param sellerModel New Seller to create
     * @return The created Seller
     */
    @Override
    public SellerModel save(SellerModel sellerModel) {
        if(cpfDuplicate(sellerModel.getCpf()))
            throw new CpfDuplicateException(sellerModel.getCpf());
        return repo.save(sellerModel);
    }

    /**
     * Returns all stored Seller
     * @return List of all Seller
     */
    @Override
    public List<SellerModel> getAll() {
        return repo.findAll();
    }

    /**
     * Returns a stored Seller given ID
     * @param id
     * @return The stored Seller
     * @throws SellerByIdNotFoundException Throws in case Seller does not exists
     */
    @Override
    public SellerModel getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new SellerByIdNotFoundException(id));
    }

    /**
     * Updates a stored Seller
     * @param sellerModel Used as reference to update stored Seller. Must contains ID with existent Seller
     * @throws SellerByIdNotFoundException Throws in case Seller does not exists
     */
    @Override
    public SellerModel update(SellerModel sellerModel, Long id) {
        SellerModel seller = getById(id);
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

    /**
     * Return true when cpf already exists
     * @param cpf
     * @return boolean
     */
    public boolean cpfDuplicate(String cpf){
        Optional<SellerModel> seller = findByCpf(cpf);
        return seller.isPresent();
    }
}
