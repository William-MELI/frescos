package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.RepresentativeRequest;
import com.meli.frescos.controller.dto.RepresentativeResponse;
import com.meli.frescos.model.RepresentativeModel;
import com.meli.frescos.service.IRepresentativeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/representative")
public class RepresentativeController {

    private final IRepresentativeService iRepresentativeService;

    public RepresentativeController(IRepresentativeService iRepresentativeService) {
        this.iRepresentativeService = iRepresentativeService;
    }

    @GetMapping
    ResponseEntity<List<RepresentativeResponse>> getAll() throws Exception {
        List<RepresentativeResponse> representativeResponseList = iRepresentativeService.getAll().stream().map(RepresentativeResponse::toResponse).toList();
        return new ResponseEntity<>(representativeResponseList, HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    ResponseEntity<RepresentativeResponse> getById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(RepresentativeResponse.toResponse(iRepresentativeService.getById(id)), HttpStatus.FOUND);
    }

    @PostMapping
    ResponseEntity<RepresentativeResponse> save(@RequestBody RepresentativeRequest representativeRequest) {
        RepresentativeModel representative = representativeRequest.toRepresentative();
        RepresentativeResponse representativeResponse = RepresentativeResponse.toResponse(iRepresentativeService.save(representative, representativeRequest.getWarehouseCode()));
        return new ResponseEntity<>(representativeResponse, HttpStatus.CREATED);

    }

}
