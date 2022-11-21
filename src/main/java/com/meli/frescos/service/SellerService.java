package com.meli.frescos.service;

import com.meli.frescos.exception.CpfDuplicateException;
import com.meli.frescos.exception.SellerByIdNotFoundException;
import com.meli.frescos.model.SellerModel;
import com.meli.frescos.repository.SellerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class contains all Seller related functions
 * Using @Service from spring
 */
@Service
public class SellerService implements ISellerService {

    private final SellerRepository sellerRepository;

    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    /**
     * Create a new Seller given model
     *
     * @param sellerModel New Seller to create
     * @return The created Seller
     */
    @Override
    public SellerModel save(SellerModel sellerModel) {
        return !cpfAlreadyExists(sellerModel.getCpf()) ? sellerRepository.save(sellerModel) : null;
    }

    /**
     * Returns all stored Seller
     *
     * @return List of all Seller
     */
    @Override
    public List<SellerModel> getAll() {
        return sellerRepository.findAll();
    }

    /**
     * Returns a stored Seller given ID
     *
     * @param id the seller id
     * @return The stored Seller
     * @throws SellerByIdNotFoundException Throws in case Seller does not exist
     */
    @Override
    public SellerModel getById(Long id) {
        return sellerRepository.findById(id).orElseThrow(() -> new SellerByIdNotFoundException(id));
    }

    /**
     * Updates a stored Seller
     *
     * @param sellerModel Used as reference to update stored Seller. Must contain ID with existent Seller
     * @throws SellerByIdNotFoundException Throws in case Seller does not exist
     */
    @Override
    public SellerModel update(SellerModel sellerModel, Long id) {
        SellerModel seller = getById(id);
        sellerModel.setId(seller.getId());
        return sellerRepository.save(sellerModel);
    }

    /**
     * Deletes a Seller given ID
     *
     * @param id Existent Seller ID
     */
    @Override
    public void deleteById(Long id) {
        SellerModel sellerModel = getById(id);
        if (sellerModel != null)
            sellerRepository.deleteById(id);

    }

    /**
     * Returns a stored Seller given cpf
     *
     * @param cpf the seller cpf
     * @return The stored Seller
     */
    @Override
    public Optional<SellerModel> getByCpf(String cpf) {
        return sellerRepository.findByCpf(cpf);
    }

    /**
     * Return false when cpf does not exist
     *
     * @param cpf the seller cpf
     * @return returns false when the cpf does not exist stored
     */
    private boolean cpfAlreadyExists(String cpf) {
        getByCpf(cpf).ifPresent(n -> {
            throw new CpfDuplicateException(cpf);
        });
        return false;
    }
}
