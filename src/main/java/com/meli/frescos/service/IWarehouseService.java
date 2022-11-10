package com.meli.frescos.service;

import com.meli.frescos.model.WarehouseModel;

import java.util.List;

public interface IWarehouseService {

    WarehouseModel save(WarehouseModel warehouse);

    WarehouseModel getById(Long id);

    List<WarehouseModel> getAll();

    void update(WarehouseModel warehouse);

    void delete(Long id);
}
