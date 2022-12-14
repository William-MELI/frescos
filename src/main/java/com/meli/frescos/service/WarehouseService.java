package com.meli.frescos.service;

import com.meli.frescos.exception.UsedPrimaryKeyConstraintException;
import com.meli.frescos.exception.WarehouseNotFoundException;
import com.meli.frescos.model.SectionModel;
import com.meli.frescos.model.WarehouseModel;
import com.meli.frescos.repository.WarehouseRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 *  This class contains all Warehouse related functions
 *  Using @Service from spring
 */
@Service
public class WarehouseService implements IWarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
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
     * @throws WarehouseNotFoundException Throws in case Warehouse does not exist
     */
    public WarehouseModel getById(Long id) throws WarehouseNotFoundException {
        Optional<WarehouseModel> warehouseModelOptional = this.warehouseRepository.findById(id);
        if (warehouseModelOptional.isEmpty()) {
            String msg = String.format("Warehouse com ID %d não encontrado", id);
            throw new WarehouseNotFoundException(msg);
        } else {
            return warehouseModelOptional.get();
        }

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
     * Deletes a Warehouse given ID
     *
     * @param id Existent Warehouse ID
     * @throw UsedPrimaryKeyConstraintException Throws in case Warehouse is related with a Section
     */
    public void delete(Long id) throws UsedPrimaryKeyConstraintException {
        List<SectionModel> sectionList = warehouseRepository.findSectionByWarehouseModelId(id);
        if (sectionList.isEmpty()) {
            this.warehouseRepository.deleteById(id);
        } else {
            String msg = String.format("Warehouse está relacionada com %d Section's. Delete-os primeiro.", sectionList.size());
            throw new UsedPrimaryKeyConstraintException(msg);
        }
    }
}
