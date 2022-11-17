package com.meli.frescos.service;

import com.meli.frescos.exception.UsedPrimaryKeyConstraintException;
import com.meli.frescos.exception.WarehouseNotFoundException;
import com.meli.frescos.model.WarehouseModel;

import java.util.List;

public interface IWarehouseService {

    WarehouseModel save(WarehouseModel warehouse);

    WarehouseModel getById(Long id) throws WarehouseNotFoundException;

    List<WarehouseModel> getAll();

    void delete(Long id) throws UsedPrimaryKeyConstraintException;
}
