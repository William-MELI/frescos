package com.meli.frescos.service;

import com.meli.frescos.controller.dto.SectionRequest;
import com.meli.frescos.model.SectionModel;

import java.util.List;

public interface ISectionService {

    List<SectionModel> findAll();

    SectionModel save(SectionRequest sectionRequest);

    SectionModel findById(Long id) throws Exception;
}
