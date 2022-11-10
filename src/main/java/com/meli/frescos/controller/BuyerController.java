package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.BuyerRequest;
import com.meli.frescos.controller.dto.BuyerResponse;
import com.meli.frescos.exception.BuyerNotFoundException;
import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.service.IBuyerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @RestController to Buyer
 */
@RestController
@RequestMapping("/buyer")
public class BuyerController {

    private final IBuyerService service;

    public BuyerController(IBuyerService service) {
        this.service = service;
    }

    /**
     * Creates a new Buyer instance.
     * Returns 201 CREATED when operation is success
     *
     * @param buyerRequest the Buyer instance
     * @return a Buyer instance
     */
    @PostMapping
    public ResponseEntity<BuyerResponse> save(@Valid @RequestBody BuyerRequest buyerRequest) {
        BuyerModel buyer = buyerRequest.toModel();
        BuyerResponse response = BuyerResponse.toResponse(service.save(buyer));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Return all Buyer
     * Return 200 OK when operation is success
     *
     * @return a list with all Buyer instance
     */
    @GetMapping
    public ResponseEntity<List<BuyerResponse>> getAll() {
        List<BuyerResponse> buyerResponseList = service.getAll().stream().map(BuyerResponse::toResponse).toList();
        return new ResponseEntity<>(buyerResponseList, HttpStatus.FOUND);
    }

    /**
     * Return a Buyer given id
     * Return 200 OK when operation is success
     *
     * @param id the Buyer ID
     * @return the Buyer instance related id
     */
    @GetMapping("/{id}")
    public ResponseEntity<BuyerResponse> getById(@PathVariable Long id) throws BuyerNotFoundException {
        return new ResponseEntity<>(BuyerResponse.toResponse(service.getById(id)), HttpStatus.FOUND);
    }

    /**
     * Update a Buyer by id
     * Return 200 OK when operation is success
     *
     * @param buyerRequest the Buyer instance
     * @param id           the Buyer id
     * @return the Buyer instance updated
     */
    @PutMapping("/{id}")
    public ResponseEntity<BuyerResponse> update(@Valid @RequestBody BuyerRequest buyerRequest, @PathVariable Long id) throws BuyerNotFoundException {
        return new ResponseEntity<>(BuyerResponse.toResponse(service.update(buyerRequest.toModel(), id)), HttpStatus.OK);
    }

//    /**
//     * Delete a Buyer by id
//     * Return 204 NO CONTENT when success
//     *
//     * @param id the Buyer id
//     * @return a ResponseEntity<Void> instance
//     */
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
//        service.deleteById(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}
