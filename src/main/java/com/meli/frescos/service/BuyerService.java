package com.meli.frescos.service;

import com.meli.frescos.exception.BuyerNotFoundException;
import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.repository.BuyerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class contains all Buyer related functions
 * Using @Service from spring
 */
@Service
public class BuyerService implements IBuyerService {

    private final BuyerRepository buyerRepository;

    public BuyerService(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    /**
     * Saves and return a Buyer entity
     *
     * @param buyerModel The new Buyer entity
     * @return The new BuyerModel entity
     */
    @Override
    public BuyerModel save(BuyerModel buyerModel) {
        return buyerRepository.save(buyerModel);
    }

    /**
     * Queries and returns a list of BuyerModel
     *
     * @return A list of all BuyerModel
     */
    @Override
    public List<BuyerModel> getAll() {
        return buyerRepository.findAll();
    }

    /**
     * Queries and return a BuyerModel based in its ID
     *
     * @param id The ID used as filter
     * @return The requested BuyerModel
     * @throws BuyerNotFoundException Throws exception in case the BuyerModel with this ID does not exist
     */
    @Override
    public BuyerModel getById(Long id) throws BuyerNotFoundException {
        return buyerRepository.findById(id).orElseThrow(() -> new BuyerNotFoundException(String.format("Comprador com ID %d não encontrado", id)));
    }

    /**
     * Updates a BuyerModel and returns the new version.
     *
     * @param buyerModel The BuyerModel to update
     * @param id         The ID of BuyerModel to filter
     * @return The updated BuyerModel
     * @throws BuyerNotFoundException Throws exception in case the BuyerModel with this ID does not exist
     */
    @Override
    public BuyerModel update(BuyerModel buyerModel, Long id) throws BuyerNotFoundException {
        BuyerModel buyer = getById(id);
        buyerModel.setId(buyer.getId());
        return buyerRepository.save(buyerModel);
    }

    /**
     * Queries and return a BuyerModel based in its CPF
     *
     * @param cpf The CPF used as filter
     * @return The requested BuyerModel
     * @throws BuyerNotFoundException Throws exception in case the BuyerModel with this CPF does not exist
     */
    @Override
    public Optional<BuyerModel> getByCpf(String cpf) {
        return buyerRepository.findByCpf(cpf);
    }
}
