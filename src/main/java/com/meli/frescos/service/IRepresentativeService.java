package com.meli.frescos.service;

import com.meli.frescos.exception.WarehouseNotFoundException;
import com.meli.frescos.model.RepresentativeModel;

import java.util.List;

public interface IRepresentativeService {
    RepresentativeModel getById(Long representativeId) throws Exception;

    List<RepresentativeModel> getAll() throws Exception;

    RepresentativeModel save(RepresentativeModel representativeModel, Long warehouseCode) throws WarehouseNotFoundException;

    void validateRepresentative(Long representativeId, Long warehouseId) throws Exception;
}
