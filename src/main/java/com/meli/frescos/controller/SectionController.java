package com.meli.frescos.controller;

import com.meli.frescos.model.SectionModel;
import com.meli.frescos.service.ISectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/section")
public class SectionController {

    @Autowired
    private ISectionService service;

    @GetMapping
    public ResponseEntity<List<SectionModel>> findAll() {
        List<SectionModel> findAllSections = service.findAll();
        return new ResponseEntity<>(findAllSections, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SectionModel> insert(@RequestBody SectionModel sectionModel) {
        SectionModel insertSection = service.insert(sectionModel);
        return new ResponseEntity<>(insertSection, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectionModel> findById(@PathVariable Long id) {
        Optional<SectionModel> section = service.findById(id);

        if(section.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(section.get(), HttpStatus.OK);
    }
}
