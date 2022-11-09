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

    public WarehouseModel create(WarehouseModel warehouse){
        this.warehouseRepository.save(warehouse);
        return null;
    }

    public WarehouseModel getById(Long id){
        WarehouseModel warehouse = this.warehouseRepository.findById(id).orElseThrow(NullPointerException::new);
        return warehouse;
    }

    public List<WarehouseModel> getAll(){
        return (List) this.warehouseRepository.findAll();
    }

    public void update(WarehouseModel warehouseUpdate){
        Optional<WarehouseModel> warehouseOpt = this.warehouseRepository.findById(warehouseUpdate.getId());
        if(warehouseOpt.isEmpty()){
            throw new NullPointerException("Warehouse não encontrado");
        }

        this.warehouseRepository.save(warehouseUpdate);

    }
    public void delete(Long id){
        this.warehouseRepository.deleteById(id);

    }
}
