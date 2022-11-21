package com.meli.frescos.service;

import com.meli.frescos.exception.OneToOneMappingAlreadyDefinedException;
import com.meli.frescos.exception.RepresentativeNotFoundException;
import com.meli.frescos.exception.RepresentativeWarehouseNotAssociatedException;
import com.meli.frescos.exception.WarehouseNotFoundException;
import com.meli.frescos.model.RepresentativeModel;
import com.meli.frescos.repository.RepresentativeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * This class contains all Representative related functions
 * Using @Service from spring
 */
@Service
public class RepresentativeService implements IRepresentativeService {

    private final RepresentativeRepository representativeRepository;

    private final IWarehouseService iWarehouseService;

    public RepresentativeService(RepresentativeRepository representativeRepository, IWarehouseService iWarehouseService) {
        this.representativeRepository = representativeRepository;
        this.iWarehouseService = iWarehouseService;
    }

    /**
     * Return RepresentativeModel given id
     *
     * @param representativeId the RepresentativeModel id
     * @return RepresentativeModel
     * @throws Exception when Representative not found
     */
    @Override
    public RepresentativeModel getById(Long representativeId) throws RepresentativeNotFoundException {
        return representativeRepository.findById(representativeId).orElseThrow(() -> new RepresentativeNotFoundException("Representative not found."));
    }

    /**
     * Return all Representative
     *
     * @return List of RepresentativeModel
     * @throws Exception
     */
    @Override
    public List<RepresentativeModel> getAll(){
        return representativeRepository.findAll();
    }

    /**
     * Create a new RepresentativeModel given model
     *
     * @param representativeModel new RepresentativeModel to create
     * @param warehouseCode warehouse id related to representative
     * @return the RepresentativeModel created
     * @throws WarehouseNotFoundException when warehouse not found
     * @throws OneToOneMappingAlreadyDefinedException when warehouse is already related to another representative
     */
    @Override
    public RepresentativeModel save(RepresentativeModel representativeModel, Long warehouseCode) throws WarehouseNotFoundException, OneToOneMappingAlreadyDefinedException {
        representativeModel.setWarehouse(iWarehouseService.getById(warehouseCode));
        boolean alreadyDefined = verifyWarehouseAlreadyDefined(warehouseCode);
        if(alreadyDefined)
            throw new OneToOneMappingAlreadyDefinedException(String.format("Warehouse de ID %d já está relacionado com um Representative na base de dados.", warehouseCode));
        return representativeRepository.save(representativeModel);
    }

    /**
     * Checks if the representative belongs to the warehouse
     *
     * @param representativeId representative id
     * @param warehouseId warehouse id
     * @throws Exception when representative does not belong to the warehouse
     */
    @Override
    public void validateRepresentative(Long representativeId, Long warehouseId) throws RepresentativeWarehouseNotAssociatedException, RepresentativeNotFoundException {
        RepresentativeModel representative = getById(representativeId);
        if(!representative.getWarehouse().getId().equals(warehouseId)) {
            throw new RepresentativeWarehouseNotAssociatedException("Representante não pertence a este armazém!");
        };
    }

    /**
     * Checks if the warehouse is already related to a representative
     *
     * @param warehouseCode warehouse id
     * @return true when warehouse is already related to a representative and false when the warehouse has no representative
     */
    private boolean verifyWarehouseAlreadyDefined(Long warehouseCode){
        RepresentativeModel model = representativeRepository.findRepresentativeModelByWarehouseId(warehouseCode);
        return model != null;
    }
}
