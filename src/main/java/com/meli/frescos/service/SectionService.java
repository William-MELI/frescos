package com.meli.frescos.service;

import com.meli.frescos.controller.dto.SectionRequest;
import com.meli.frescos.model.SectionModel;
import com.meli.frescos.repository.SectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService implements ISectionService {

    @Autowired
    private SectionRepo repo;

    @Override
    public List<SectionModel> findAll() {
        return repo.findAll();
    }

    @Override
    public SectionModel insert(SectionRequest sectionRequest) {
        SectionModel model = new SectionModel(sectionRequest.getDescription(),
                sectionRequest.getCategory(),
                sectionRequest.getTotalSize(),
                sectionRequest.getTemperature(),
                sectionRequest.getWarehouse());

       return repo.save(model);
    }

    @Override
    public Optional<SectionModel> findById(Long id) {
        return repo.findById(id);
    }
}
