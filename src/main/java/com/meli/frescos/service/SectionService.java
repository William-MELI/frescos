package com.meli.frescos.service;

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
    public SectionModel insert(SectionModel sectionModel) {
        return repo.save(sectionModel);
    }

    @Override
    public Optional<SectionModel> findById(Long id) {
        return repo.findById(id);
    }
}
