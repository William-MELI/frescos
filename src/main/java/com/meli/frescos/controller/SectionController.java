package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.SectionRequest;
import com.meli.frescos.model.SectionModel;
import com.meli.frescos.service.ISectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/section")
public class SectionController {

    @Autowired
    private ISectionService service;

    @GetMapping
    ResponseEntity<List<SectionModel>> findAll() {
        List<SectionModel> findAllSections = service.findAll();
        return new ResponseEntity<>(findAllSections, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<SectionModel> insert(@RequestBody @Valid SectionRequest sectionRequest) {
        SectionModel insertSection = service.insert(sectionRequest);
        return new ResponseEntity<>(insertSection, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<SectionModel> findById(@PathVariable Long id) {
        SectionModel section = service.findById(id);
        return new ResponseEntity<>(section, HttpStatus.OK);
    }
}
