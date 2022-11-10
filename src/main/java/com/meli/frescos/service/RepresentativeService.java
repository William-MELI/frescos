package com.meli.frescos.service;

import com.meli.frescos.exception.WarehouseNotFoundException;
import com.meli.frescos.model.RepresentativeModel;
import com.meli.frescos.repository.RepresentativeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepresentativeService implements IRepresentativeService {

    private final RepresentativeRepository representativeRepository;

    private final IWarehouseService iWarehouseService;

    public RepresentativeService(RepresentativeRepository representativeRepository, IWarehouseService iWarehouseService) {
        this.representativeRepository = representativeRepository;
        this.iWarehouseService = iWarehouseService;
    }

    @Override
    public RepresentativeModel getById(Long representativeId) throws Exception {
        return representativeRepository.findById(representativeId).orElseThrow(() -> new Exception("Representative not found."));
    }

    @Override
    public List<RepresentativeModel> getAll() throws Exception {
        return representativeRepository.findAll();
    }

    @Override
    public RepresentativeModel save(RepresentativeModel representativeModel, Long warehouseCode) throws WarehouseNotFoundException {
        representativeModel.setWarehouse(iWarehouseService.getById(warehouseCode));
        return representativeRepository.save(representativeModel);
    }

    @Override
    public boolean permittedRepresentative(RepresentativeModel representative, Long warehouseId) {
        return representative.getId().equals(warehouseId);
    }
}
