package com.meli.frescos.controller;

import com.meli.frescos.model.SectionModel;
import com.meli.frescos.service.ISectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/section")
public class SectionController {

    @Autowired
    private ISectionService service;

    @GetMapping
    public ResponseEntity<List<SectionModel>> findAll() {
        List<SectionModel> findAllSections = service.findAll();
        return ResponseEntity.ok(findAllSections);
    }

    @PostMapping
    public ResponseEntity<SectionModel> insert(@RequestBody SectionModel sectionModel) {
        SectionModel insertSection = service.insert(sectionModel);
        return new ResponseEntity<>(insertSection, HttpStatus.CREATED);
    }
}
