package com.meli.frescos.controller;

import com.meli.frescos.model.SectionModel;
import com.meli.frescos.service.ISectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SectionController {

    @Autowired
    private ISectionService service;

    @GetMapping("/section")
    public ResponseEntity<List<SectionModel>> findAll() {
        List<SectionModel> findAllSections = service.findAll();
        return ResponseEntity.ok(findAllSections);
    }
}
