package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.SectionRequest;
import com.meli.frescos.controller.dto.SectionResponse;
import com.meli.frescos.model.SectionModel;
import com.meli.frescos.service.ISectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/section")
public class SectionController {

    private final ISectionService service;

    public SectionController(ISectionService service) {
        this.service = service;
    }

    @GetMapping
    ResponseEntity<List<SectionResponse>> getAll() {
        List<SectionResponse> findAllSections = service.findAll().stream().map(SectionResponse::toResponse).toList();
        return new ResponseEntity<>(findAllSections, HttpStatus.FOUND);
    }

    @PostMapping
    ResponseEntity<SectionResponse> save(@RequestBody @Valid SectionRequest sectionRequest) {
        SectionModel insertSection = service.save(sectionRequest);
        return new ResponseEntity<>(SectionResponse.toResponse(insertSection), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<SectionResponse> getById(@PathVariable Long id) throws Exception {
        SectionModel section = service.findById(id);
        return new ResponseEntity<>(SectionResponse.toResponse(section), HttpStatus.FOUND);
    }
}
