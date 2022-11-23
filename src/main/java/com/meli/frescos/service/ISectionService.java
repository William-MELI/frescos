package com.meli.frescos.service;

import com.meli.frescos.controller.dto.SectionRequest;
import com.meli.frescos.exception.SectionByIdNotFoundException;
import com.meli.frescos.exception.WarehouseNotFoundException;
import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.model.SectionModel;

import java.util.List;

public interface ISectionService {

    List<SectionModel> getAll();

    SectionModel save(SectionRequest sectionRequest) throws WarehouseNotFoundException;

    SectionModel getById(Long id) throws SectionByIdNotFoundException;

    List<SectionModel> getByCategory(CategoryEnum category);

    SectionModel setTemperature(Long sectionId, Double temperature);
}
