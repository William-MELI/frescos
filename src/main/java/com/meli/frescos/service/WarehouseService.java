package com.meli.frescos.service;

import com.meli.frescos.model.SectionModel;
import com.meli.frescos.model.WarehouseModel;
import com.meli.frescos.repository.IWarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService implements IWarehouseService {

    private final IWarehouseRepository warehouseRepository;

    public WarehouseService(IWarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    /**
     * Create a new Warehouse given model
     *
     * @param warehouse New Warehouse to create
     * @return The created Warehouse
     */
    public WarehouseModel save(WarehouseModel warehouse) {
        return this.warehouseRepository.save(warehouse);
    }

    /**
     * Returns a stored Warehouse given ID
     *
     * @param id warehouse id
     * @return The stored Warehouse
     * @throws NullPointerException Throws in case Warehouse does not exist
     */
    public WarehouseModel getById(Long id) {
        return this.warehouseRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    /**
     * Returns all stored Warehouse
     *
     * @return List of all Warehouse
     */
    public List<WarehouseModel> getAll() {
        return this.warehouseRepository.findAll();
    }

    /**
     * Updates a stored Warehouse
     *
     * @param warehouseUpdate Used as reference to update stored Warehouse. Must contain ID with existent Warehouse
     * @throws NullPointerException Throws in case Warehouse does not exist
     */
    public void update(WarehouseModel warehouseUpdate) {
        Optional<WarehouseModel> warehouseOpt = this.warehouseRepository.findById(warehouseUpdate.getId());
        if (warehouseOpt.isEmpty()) {
            throw new NullPointerException("Warehouse not found");
        }

        this.warehouseRepository.save(warehouseUpdate);

    }

    /**
     * Deletes a Warehouse given ID
     *
     * @param id Existent Warehouse ID
     */
    public void delete(Long id) {
        List<SectionModel> sectionList = warehouseRepository.findSectionByWarehouseModelId(id);
        if (sectionList.isEmpty()) {
            this.warehouseRepository.deleteById(id);
        } else {
            throw new NullPointerException("Warehouse is related with Section. Delete it first.");
        }
    }
}
