package com.meli.frescos.service;

import com.meli.frescos.model.WarehouseModel;
import com.meli.frescos.repository.IWarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService implements  IWarehouseService{

    @Autowired
    IWarehouseRepository warehouseRepository;

    /**
     * Create a new Warehouse given model
     * @param warehouse New Warehouse to create
     * @return The created Warehouse
     */
    public WarehouseModel create(WarehouseModel warehouse){
        WarehouseModel createdWarehouse = this.warehouseRepository.save(warehouse);
        return createdWarehouse;
    }

    /**
     * Returns a stored Warehouse given ID
     * @param id
     * @return The stored Warehouse
     * @throws NullPointerException Throws in case Warehouse does not exists
     */
    public WarehouseModel getById(Long id){
        WarehouseModel warehouse = this.warehouseRepository.findById(id).orElseThrow(NullPointerException::new);
        return warehouse;
    }

    /**
     * Returns all stored Warehouse
     * @return List of all Warehouse
     */
    public List<WarehouseModel> getAll(){
        return (List) this.warehouseRepository.findAll();
    }

    /**
     * Updates a stored Warehouse
     * @param warehouseUpdate Used as reference to update stored Warehouse. Must contains ID with existent Warehouse
     * @throws NullPointerException Throws in case Warehouse does not exists
     */
    public void update(WarehouseModel warehouseUpdate){
        Optional<WarehouseModel> warehouseOpt = this.warehouseRepository.findById(warehouseUpdate.getId());
        if(warehouseOpt.isEmpty()){
            throw new NullPointerException("Warehouse not found");
        }

        this.warehouseRepository.save(warehouseUpdate);

    }

    /**
     * Deletes a Warehouse given ID
     * @param id Existent Warehouse ID
     */
    public void delete(Long id){
        this.warehouseRepository.deleteById(id);

    }
}
