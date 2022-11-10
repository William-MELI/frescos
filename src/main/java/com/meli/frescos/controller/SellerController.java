package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.SellerRequest;
import com.meli.frescos.controller.dto.SellerResponse;
import com.meli.frescos.service.ISellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * All endpoints related to Seller
 * Is`s a Spring @RestController
 */
@RestController
@RequestMapping("/seller")
public class SellerController {

    private final ISellerService service;

    public SellerController(ISellerService service) {
        this.service = service;
    }

    /**
     * Creates a new Seller instance.
     * Returns 201 CREATED when operation is success
     * @param sellerRequest the Seller instance
     * @return a Seller instance
     */
    @PostMapping
    public ResponseEntity<SellerResponse> save(@RequestBody SellerRequest sellerRequest) {
        return new ResponseEntity<>(SellerResponse.toResponse(service.save(sellerRequest.toModel())), HttpStatus.CREATED);
    }

    /**
     * Return all Seller
     * Return 200 OK when operation is success
     * @return a list with all Seller instance
     */
    @GetMapping
    public ResponseEntity<List<SellerResponse>> getAll() {
        List<SellerResponse> sellerResponseList = service.findAll().stream().map(SellerResponse::toResponse).toList();
        return new ResponseEntity<>(sellerResponseList, HttpStatus.FOUND);
    }

    /**
     * Return a Seller given id
     * Return 200 OK when operation is success
     * @param id the Seller ID
     * @return the Seller instance related id
     */
    @GetMapping("/{id}")
    public ResponseEntity<SellerResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(SellerResponse.toResponse(service.findById(id)), HttpStatus.FOUND);
    }

    /**
     * Update a Seller by id
     * Return 200 OK when operation is success
     * @param sellerRequest the Seller instance
     * @param id the Seller id
     * @return the Seller instance updated
     */
    @PutMapping("/{id}")
    public ResponseEntity<SellerResponse> update(@RequestBody SellerRequest sellerRequest, @PathVariable Long id) {
        return new ResponseEntity<>(SellerResponse.toResponse(service.update(sellerRequest.toModel(), id)), HttpStatus.OK);
    }

    /**
     * Delete a Seller by id
     * Return 204 NO CONTENT when success
     * @param id the Seller id
     * @return a ResponseEntity<Void> instance
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
