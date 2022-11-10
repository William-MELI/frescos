package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.SellerRequest;
import com.meli.frescos.controller.dto.SellerResponse;
import com.meli.frescos.service.ISellerService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SellerController {

    private final ISellerService service;

    /**
     * Creates a new Seller instance.
     * Returns 201 CREATED when operation is success
     * @param sellerRequest the Seller instance
     * @return a Seller instance
     */
    @PostMapping()
    public ResponseEntity<SellerResponse> save(@RequestBody SellerRequest sellerRequest){
        return new ResponseEntity<>(SellerResponse.toResponse(service.save(sellerRequest.toEntity())), HttpStatus.CREATED);
    }

    /**
     * Return all Seller
     * Return 200 OK when operation is success
     * @return a list with all Seller instance
     */
    @GetMapping()
    public ResponseEntity<List<SellerResponse>> findAll(){
        List<SellerResponse> sellerResponseList = service.findAll().stream().map(s -> SellerResponse.toResponse(s)).toList();
        return new ResponseEntity<>(sellerResponseList,HttpStatus.OK);
    }

    /**
     * Return a Seller given id
     * Return 200 OK when operation is success
     * @param id the Seller ID
     * @return the Seller instance related id
     */
    @GetMapping("/filter-id")
    public ResponseEntity<SellerResponse> findById(@RequestParam Long id){
        return new ResponseEntity<>(SellerResponse.toResponse(service.findById(id)) , HttpStatus.OK);
    }

    /**
     * Update a Seller by id
     * Return 200 OK when operation is success
     * @param sellerRequest the Seller instance
     * @param id the Seller id
     * @return the Seller instance updated
     */
    @PatchMapping()
    public ResponseEntity<SellerResponse> update(@RequestBody SellerRequest sellerRequest, @RequestParam Long id){
        return new ResponseEntity<>(SellerResponse.toResponse(service.update(sellerRequest.toEntity(), id)), HttpStatus.OK);
    }

    /**
     * Delete a Seller by id
     * Return 204 NO CONTENT when success
     * @param id the Seller id
     * @return a ResponseEntity<Void> instance
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteById(Long id){
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
