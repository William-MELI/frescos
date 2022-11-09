package com.meli.frescos.service;

import com.meli.frescos.model.SectionModel;

import java.util.List;

public interface ISectionService {

    List<SectionModel> findAll();

    SectionModel insert(SectionModel sectionModel);
}
