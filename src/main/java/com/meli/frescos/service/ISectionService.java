package com.meli.frescos.service;

import com.meli.frescos.controller.dto.SectionRequest;
import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.model.SectionModel;

import java.util.List;

public interface ISectionService {

    List<SectionModel> getAll();

    SectionModel save(SectionRequest sectionRequest);

    SectionModel getById(Long id);

    List<SectionModel> getByCategory(CategoryEnum category);
}
