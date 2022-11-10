package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.SellerRequest;
import com.meli.frescos.controller.dto.SellerResponse;
import com.meli.frescos.service.ISellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    private final ISellerService service;

    public SellerController(ISellerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SellerResponse> save(@RequestBody SellerRequest sellerRequest) {
        return new ResponseEntity<>(SellerResponse.toResponse(service.save(sellerRequest.toModel())), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SellerResponse>> getAll() {
        List<SellerResponse> sellerResponseList = service.findAll().stream().map(SellerResponse::toResponse).toList();
        return new ResponseEntity<>(sellerResponseList, HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(SellerResponse.toResponse(service.findById(id)), HttpStatus.FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SellerResponse> update(@RequestBody SellerRequest sellerRequest, @PathVariable Long id) {
        return new ResponseEntity<>(SellerResponse.toResponse(service.update(sellerRequest.toModel(), id)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
