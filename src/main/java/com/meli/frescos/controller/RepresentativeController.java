package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.RepresentativeRequest;
import com.meli.frescos.controller.dto.RepresentativeResponse;
import com.meli.frescos.exception.WarehouseNotFoundException;
import com.meli.frescos.model.RepresentativeModel;
import com.meli.frescos.service.IRepresentativeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * All endpoints related to Representative
 * Is`s a Spring @RestController
 */
@RestController
@RequestMapping("/representative")
public class RepresentativeController {

    private final IRepresentativeService iRepresentativeService;

    public RepresentativeController(IRepresentativeService iRepresentativeService) {
        this.iRepresentativeService = iRepresentativeService;
    }

    /**
     * Endpoint to return all Representative
     * @return a List with all Representative with status 200 ok
     */
    @GetMapping
    ResponseEntity<List<RepresentativeResponse>> getAll() {
        List<RepresentativeResponse> representativeResponseList = iRepresentativeService.getAll().stream().map(RepresentativeResponse::toResponse).toList();
        return new ResponseEntity<>(representativeResponseList, HttpStatus.OK);
    }

    /**
     * Endpoint to return a Representative given id
     * @param id the Representative id
     * @return a RepresentativeModell related ID with status 200 ok
     * @throws RepresentativeNotFoundException Throws in case Representative does not exists
     */
    @GetMapping("/{id}")
    ResponseEntity<RepresentativeResponse> getById(@PathVariable Long id) throws RepresentativeNotFoundException {
        return new ResponseEntity<>(RepresentativeResponse.toResponse(iRepresentativeService.getById(id)), HttpStatus.OK);
    }

    /**
     * POST endpoint to store a {@link RepresentativeModel}.
     * @param representativeRequest
     * @return ResponseEntity<RepresentativeResponse> with status 201 created
     * @throws WarehouseNotFoundException when warehouse not found
     * @throws OneToOneMappingAlreadyDefinedException when warehouse is already related to another representative
     */
    @PostMapping
    ResponseEntity<RepresentativeResponse> save(@Valid @RequestBody RepresentativeRequest representativeRequest) throws WarehouseNotFoundException {
        RepresentativeModel representative = representativeRequest.toRepresentative();
        RepresentativeResponse representativeResponse = RepresentativeResponse.toResponse(iRepresentativeService.save(representative, representativeRequest.getWarehouseCode()));
        return new ResponseEntity<>(representativeResponse, HttpStatus.CREATED);

    }

}
