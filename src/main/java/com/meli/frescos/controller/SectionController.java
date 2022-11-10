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


/**
 * All endpoints related to Section
 * Is`s a Spring @RestController
 */
@RestController
@RequestMapping("/section")
public class SectionController {

    @Autowired
    private ISectionService service;

    /**
     * Endpoint to return all Sections
     * @return a List with all SectionModel with status 200 ok
     */
    @GetMapping
    ResponseEntity<List<SectionModel>> findAll() {
        List<SectionModel> findAllSections = service.findAll();
        return new ResponseEntity<>(findAllSections, HttpStatus.OK);
    }

    /**
     * POST endpoint to store a {@link SectionModel}.
     *
     * @param sectionRequest
     * @return ResponseEntity<SectionModel> to the requester
     */
    @PostMapping
    ResponseEntity<SectionModel> insert(@RequestBody @Valid SectionRequest sectionRequest) {
        SectionModel insertSection = service.insert(sectionRequest);
        return new ResponseEntity<>(insertSection, HttpStatus.CREATED);
    }

    /**
     * Endpoint to return a sectionModel given id
     * @param id the sectionModel id
     * @return a SectionModel related ID
     */
    @GetMapping("/{id}")
    ResponseEntity<SectionModel> findById(@PathVariable Long id) {
        SectionModel section = service.findById(id);
        return new ResponseEntity<>(section, HttpStatus.OK);
    }
}
