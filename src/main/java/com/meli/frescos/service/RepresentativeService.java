package com.meli.frescos.service;

import com.meli.frescos.exception.OneToOneMappingAlreadyDefinedException;
import com.meli.frescos.exception.RepresentativeNotFoundException;
import com.meli.frescos.exception.RepresentativeWarehouseNotAssociatedException;
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
    public RepresentativeModel getById(Long representativeId) throws RepresentativeNotFoundException {
        return representativeRepository.findById(representativeId).orElseThrow(() -> new RepresentativeNotFoundException("Representative not found."));
    }

    @Override
    public List<RepresentativeModel> getAll(){
        return representativeRepository.findAll();
    }

    @Override
    public RepresentativeModel save(RepresentativeModel representativeModel, Long warehouseCode) throws WarehouseNotFoundException, OneToOneMappingAlreadyDefinedException {
        representativeModel.setWarehouse(iWarehouseService.getById(warehouseCode));
        boolean alreadyDefined = verifyWarehouseAlreadyDefined(warehouseCode);
        if(alreadyDefined)
            throw new OneToOneMappingAlreadyDefinedException(String.format("Warehouse de ID %d já está relacionado com um Representative na base de dados.", warehouseCode));
        return representativeRepository.save(representativeModel);
    }

    @Override
    public void validateRepresentative(Long representativeId, Long warehouseId) throws RepresentativeWarehouseNotAssociatedException, RepresentativeNotFoundException {
        RepresentativeModel representative = getById(representativeId);
        if(!representative.getWarehouse().getId().equals(warehouseId)) {
            throw new RepresentativeWarehouseNotAssociatedException("Representante não pertence a este armazém!");
        };
    }

    private boolean verifyWarehouseAlreadyDefined(Long warehouseCode){
        RepresentativeModel model = representativeRepository.findRepresentativeModelByWarehouseId(warehouseCode);
        return model != null;
    }
}
