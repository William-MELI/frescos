package com.meli.frescos.service;

import com.meli.frescos.model.SectionModel;
import com.meli.frescos.repository.SectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
