package com.meli.frescos.service;

import com.meli.frescos.exception.RefrigeratorNotFoundException;
import com.meli.frescos.model.Refrigerator;

import java.util.List;

public interface IRefrigeratorService {
    List<Refrigerator> getAll() throws RefrigeratorNotFoundException;

    Refrigerator getById(Long id) throws RefrigeratorNotFoundException;

    Refrigerator getBySectionId(Long sectionId) throws RefrigeratorNotFoundException;

    Refrigerator save(Refrigerator refrigerator);

    Refrigerator setSection(Long refrigeratorId, Long sectionId) throws RefrigeratorNotFoundException;

    Refrigerator setTemperature(Long refrigeratorId, Double temperature) throws RefrigeratorNotFoundException;

    Refrigerator setOn(Long refrigeratorId, Boolean on) throws RefrigeratorNotFoundException;

    Refrigerator executeRevision(Long refrigeratorId) throws RefrigeratorNotFoundException;
}
