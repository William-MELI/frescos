package com.meli.frescos.service;

import com.meli.frescos.controller.dto.SectionRequest;
import com.meli.frescos.model.SectionModel;
import com.meli.frescos.model.WarehouseModel;
import com.meli.frescos.repository.IWarehouseRepository;
import com.meli.frescos.repository.SectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService implements ISectionService {

    private final SectionRepository repo;

    private final IWarehouseRepository warehouseRepo;

    public SectionService(SectionRepository repo, IWarehouseRepository warehouseRepo) {
        this.repo = repo;
        this.warehouseRepo = warehouseRepo;
    }

    @Override
    public List<SectionModel> findAll() {
        return repo.findAll();
    }

    @Override
    public SectionModel save(SectionRequest sectionRequest) {
        Optional<WarehouseModel> warehouse = warehouseRepo.findById(sectionRequest.getWarehouse());
        SectionModel model = new SectionModel(sectionRequest.getDescription(),
                sectionRequest.getCategory(),
                sectionRequest.getTotalSize(),
                sectionRequest.getTemperature(),
                warehouse.get()
        );
        return repo.save(model);
    }

    @Override
    public SectionModel findById(Long id) throws Exception {

        Optional<SectionModel> responseDb = repo.findById(id);

        if (responseDb.isEmpty()) {
            throw new Exception("Section not found");
        }
        return responseDb.get();
    }
}
