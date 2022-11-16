package com.meli.frescos.service;

import com.meli.frescos.controller.dto.SectionRequest;
import com.meli.frescos.exception.SectionByIdNotFoundException;
import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.model.SectionModel;
import com.meli.frescos.model.WarehouseModel;
import com.meli.frescos.repository.IWarehouseRepository;
import com.meli.frescos.repository.SectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *  This class contains all Section related functions
 *  Using @Service from spring
 */
@Service
public class SectionService implements ISectionService {

    private final SectionRepository sectionRepository;

    private final IWarehouseRepository warehouseRepository;

    public SectionService(SectionRepository sectionRepository, IWarehouseRepository warehouseRepository) {
        this.sectionRepository = sectionRepository;
        this.warehouseRepository = warehouseRepository;
    }

    /**
     * Return all Sections
     * @return List of SectionModel
     */
    @Override
    public List<SectionModel> getAll() {
        return sectionRepository.findAll();
    }

    /**
     * Save a new Section at storage
     *
     * @param sectionRequest the new Section to store
     * @return the new created client
     */
    @Override
    public SectionModel save(SectionRequest sectionRequest) {
        Optional<WarehouseModel> warehouse = warehouseRepository.findById(sectionRequest.getWarehouse());

        if (warehouse.isEmpty()) {
            throw new NullPointerException("Warehouse not found");
        }

        SectionModel model = new SectionModel(sectionRequest.getDescription(),
                sectionRequest.getCategory(),
                sectionRequest.getTotalSize(),
                sectionRequest.getTemperature(),
                warehouse.get()
        );
        return sectionRepository.save(model);
    }

    /**
     * Return SectionModel given id
     * @param id the SectionModel id
     * @return SectionModel
     */
    @Override
    public SectionModel getById(Long id) {
        return sectionRepository.findById(id).orElseThrow(() -> new SectionByIdNotFoundException(id));
    }

    @Override
    public List<SectionModel> getByCategory(CategoryEnum category) {
        return sectionRepository.findByCategory(category);
    }
}
