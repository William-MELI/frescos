package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.SectionRequest;
import com.meli.frescos.controller.dto.SectionResponse;
import com.meli.frescos.exception.SectionByIdNotFoundException;
import com.meli.frescos.exception.WarehouseNotFoundException;
import com.meli.frescos.model.SectionModel;
import com.meli.frescos.service.ISectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * All endpoints related to Section
 * Is`s a Spring @RestController
 */
@RestController
@RequestMapping("/section")
public class SectionController {

    private final ISectionService service;

    public SectionController(ISectionService service) {
        this.service = service;
    }

    /**
     * Endpoint to return all Sections
     * @return a List with all SectionModel with status 200 ok
     */
    @GetMapping
    ResponseEntity<List<SectionResponse>> getAll() {
        List<SectionResponse> findAllSections = service.getAll().stream().map(SectionResponse::toResponse).toList();
        return new ResponseEntity<>(findAllSections, HttpStatus.OK);
    }

    /**
     * POST endpoint to store a {@link SectionModel}.
     * @param sectionRequest
     * @return ResponseEntity<SectionModel> to the requester
     * @throws WarehouseNotFoundException Throws in case Warehouse does not exists
     */
    @PostMapping
    ResponseEntity<SectionResponse> save(@RequestBody @Valid SectionRequest sectionRequest) throws WarehouseNotFoundException {
        SectionModel insertSection = service.save(sectionRequest);
        return new ResponseEntity<>(SectionResponse.toResponse(insertSection), HttpStatus.CREATED);
    }

    /**
     * Endpoint to return a sectionModel given id
     * @param id the sectionModel id
     * @return a SectionModel related ID
     * @throws SectionByIdNotFoundException Throws in case Warehouse does not exists
     */
    @GetMapping("/{id}")
    ResponseEntity<SectionResponse> getById(@PathVariable Long id) throws SectionByIdNotFoundException {
        SectionModel section = service.getById(id);
        return new ResponseEntity<>(SectionResponse.toResponse(section), HttpStatus.OK);
    }
}
