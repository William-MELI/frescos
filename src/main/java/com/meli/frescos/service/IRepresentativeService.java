package com.meli.frescos.service;

import com.meli.frescos.exception.OneToOneMappingAlreadyDefinedException;
import com.meli.frescos.exception.RepresentativeNotFoundException;
import com.meli.frescos.exception.RepresentativeWarehouseNotAssociatedException;
import com.meli.frescos.exception.WarehouseNotFoundException;
import com.meli.frescos.model.RepresentativeModel;

import java.util.List;

public interface IRepresentativeService {
    RepresentativeModel getById(Long representativeId) throws RepresentativeNotFoundException;

    List<RepresentativeModel> getAll();

    RepresentativeModel save(RepresentativeModel representativeModel, Long warehouseCode) throws WarehouseNotFoundException, OneToOneMappingAlreadyDefinedException;

    void validateRepresentative(Long representativeId, Long warehouseId) throws RepresentativeWarehouseNotAssociatedException, RepresentativeNotFoundException, WarehouseNotFoundException;
}
